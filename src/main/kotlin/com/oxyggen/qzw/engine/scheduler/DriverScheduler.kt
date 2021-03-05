package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.engine.Engine
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannelEndpoint
import com.oxyggen.qzw.engine.channel.framePrioritySelect
import com.oxyggen.qzw.engine.channel.framePrioritySelectWithTimeout
import com.oxyggen.qzw.engine.driver.Driver
import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.transport.frame.*
import kotlinx.coroutines.*
import org.apache.logging.log4j.kotlin.Logging
import kotlin.system.measureTimeMillis

class DriverScheduler(
    private val network: Network = Network(),
    private val epZW: FrameDuplexPriorityChannelEndpoint,
    private val driver: Driver
) : Logging {

    private data class FrameSendScheduleEntry(val iteration: Int, val timeout: Long, val retransmissionWait: Long)

    /********************************************************************************
     *  Iteration:       Timeout waiting for result      Wait before retransmission
     *  1                1500                            100
     *  2                1500                            1100
     *  3                1500                            2100
     *  4                1500                            3100
     ********************************************************************************/
    private val frameSendSchedule = listOf<FrameSendScheduleEntry>(
        FrameSendScheduleEntry(1, 1500, 100),
        FrameSendScheduleEntry(1, 1500, 1100),
        FrameSendScheduleEntry(1, 1500, 2100),
        FrameSendScheduleEntry(1, 1500, 0)
    )

    private val channelZWDirect = FrameDuplexPriorityChannel("Receiver", "Sender")

    suspend fun start(coroutineScope: CoroutineScope) {
        coroutineScope.launch { sender() }
        coroutineScope.launch { receiver() }
    }

    private suspend fun sender() {
        try {
            logger.info { "Driver sender started" }

            while (true) {
                val result = framePrioritySelect(channelZWDirect.endpointA, epZW)
                when (result.frame) {
                    is FrameState -> {
                        // Frame with predecessor is informing Z-Wave about SOF frame
                        // was received. We should ignore ACK frame without predecessor
                        // at this point!
                        if (result.frame.predecessor != null) {
                            // State frame with predecessor means receiver received a data frame, so we will send this state frame out to Z-Wave network
                            logger.debug("${Engine.LOG_PFX_SENDER}: Sending state frame to network, without waiting for answer. Frame: ${result.frame.toStringWithPredecessor()}")
                            driver.putFrame(result.frame)
                            result.frame.setSent()
                        } else {
                            // State frame without predecessor is expected after data frame sent to network...
                            logger.warn("${Engine.LOG_PFX_SENDER}: State frame ${result.frame}  without predecessor received. Ignoring.")
                        }
                    }
                    is FrameSOF -> {
                        val frameSOF = result.frame
                        var resultStateFrame: FrameState?
                        // Data frame received means we should send data frame to Z-Wave
                        // and wait for ACK result
                        for (schedule in frameSendSchedule) {
                            resultStateFrame = null

                            // Send the frame into network
                            driver.putFrame(frameSOF)
                            frameSOF.setSent()
                            logger.debug { "Frame sent, waiting ${schedule.timeout}ms for ACK (${schedule.iteration}/${frameSendSchedule.size}). Frame: ${frameSOF}" }

                            var remainingMillis = schedule.timeout
                            while (remainingMillis > 0 && resultStateFrame == null) {
                                val elapsedMillis = measureTimeMillis {
                                    val resultState =
                                        framePrioritySelectWithTimeout(remainingMillis, channelZWDirect.endpointA)

                                    if (resultState == null) {        // Null means timeout!
                                        logger.debug { "Timeout when waiting for ACK" }
                                        remainingMillis = -1
                                    } else {
                                        val frameState = resultState.frame as FrameState
                                        if (frameState.predecessor != null) {
                                            // State frame with predecessor means receiver received an out of order data frame,
                                            // so we need to send an ACK frame to Z-Wave network and we will continue with waiting
                                            logger.debug("${Engine.LOG_PFX_SENDER}: Out of turn state frame received, sending to network, without waiting for answer. Frame: ${result.frame.toStringWithPredecessor()}")
                                            driver.putFrame(result.frame)
                                            result.frame.setSent()
                                        } else {
                                            // State frame without predecessor is expected after data frame sent to network...
                                            if (frameState is FrameACK && frameState.created.isAfter(frameSOF.sent)) {
                                                resultStateFrame = frameState.withPredecessor(frameSOF)
                                            } else {
                                                logger.debug { "${Engine.LOG_PFX_SENDER}: Not expected out of order state frame received. Ignoring." }
                                            }

                                        }
                                    }
                                }
                                remainingMillis -= elapsedMillis
                            }

                            resultStateFrame?.let {
                                logger.debug { "${Engine.LOG_PFX_SENDER}: State frame $it received, informing framework" }
                                epZW.send(it)
                            } ?: run {
                                logger.debug { "${Engine.LOG_PFX_SENDER}: State frame not received, informing framework" }
                                epZW.send(FrameNUL(network, frameSOF))
                            }

                        }
                    }
                }


                driver.putFrame(result.frame)
                result.frame.setSent()
                logger.debug("${Engine.LOG_PFX_SENDER}: Frame sent to driver. Frame: ${result.frame}")
                delay(50)
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Driver sender stopped" }
        }

    }

    private suspend fun receiver() {
        try {
            logger.info { "Driver receiver started" }

            while (true) {
                val frame = driver.getFrame(network)
                when (frame) {
                    is FrameSOF -> {
                        logger.debug("${Engine.LOG_PFX_RECEIVER}: Data frame received from driver, sending ACK. Frame: $frame")
                        val frameACK = FrameACK(network, frame)
                        // Send ACK frame to sender, it should send back ACK to Z-Wave network
                        channelZWDirect.endpointB.send(frameACK)
                        // Send frame sequence to network scheduler
                        epZW.send(frameACK)
                    }
                    is FrameState -> {
                        logger.debug("${Engine.LOG_PFX_RECEIVER}: State frame $frame received from driver, informing sender")
                        // State frame received, sender job should be in wait state...
                        channelZWDirect.endpointB.send(frame)
                    }
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Driver receiver stopped" }
        }
    }
}