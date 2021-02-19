package com.oxyggen.qzw.engine

import com.oxyggen.qzw.engine.channel.EnginePriorityChannel
import com.oxyggen.qzw.engine.config.EngineConfig
import com.oxyggen.qzw.engine.event.EngineEventAbort
import com.oxyggen.qzw.engine.event.EngineEventFrameReceived
import com.oxyggen.qzw.engine.event.EngineEventFrameSend
import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.NetworkInfo
import com.oxyggen.qzw.transport.frame.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalUnsignedTypes::class)
class Engine(val engineConfig: EngineConfig, val coroutineScope: CoroutineScope = GlobalScope) : Logging {

    companion object {
        const val LOG_PFX_SENDER = "H -> ZW"
        const val LOG_PFX_RECEIVER = "H <- ZW"
        const val LOG_PFX_DISPATCHER = "<- * ->"
    }

    // Create dispatcher Channel (this is the entry point for all events)
    private val dispatchChannel = EnginePriorityChannel()

    // Create send Channel
    private val sendChannel = EnginePriorityChannel()

    private var executionJob: Job? = null
    private val loopResultMutex = Mutex()
    private val networkInfo = NetworkInfo()
    private val network = Network()

    fun start() = coroutineScope.launch { startWithLock() }

    fun stop(gently: Boolean = true) = coroutineScope.launch { stopWithLock(gently) }

    fun sendFrame(frame: Frame, callback: ((frame: Frame) -> Unit)? = null) {
        coroutineScope.launch {
            dispatchChannel.sendFrame(frame)
        }
    }

    suspend fun stopAndWait(gently: Boolean = true) {
        stopWithLock(gently)
        executionJob?.join()
    }

    val started: Boolean
        get() = executionJob?.isActive ?: false

    private suspend fun startWithLock() {
        loopResultMutex.withLock {
            if (!started)
                executionJob = coroutineScope.launch { executeJobs() }
        }
    }

    private suspend fun stopWithLock(gently: Boolean = true) {
        loopResultMutex.withLock {
            if (started)
                if (gently) {
                    dispatchChannel.send(EngineEventAbort())
                } else {
                    executionJob!!.cancel()
                    engineConfig.driver.stop()
                }
        }
    }

    private suspend fun executeJobs() {
        if (!engineConfig.driver.start()) {
            logger.error { "Engine - Main: Unable to start driver!" }
            return
        } else {
            logger.debug { "Engine - Main: Driver started" }
        }

        // Initialize all channels
        network.initCallbackKeys()
        //dispatchChannel.init()
        //sendChannel.init()

        // SendChannel -> Driver
        val senderJob = coroutineScope.launch { sendJob() }

        // Driver -> DispatcherChannel
        val receiverJob = coroutineScope.launch(Dispatchers.IO) { receiveJob() }

        // DispatcherChannel -> SendChannel
        val dispatcherJob = coroutineScope.launch { dispatcherJob() }

        dispatcherJob.join()
        senderJob.join()

        // Stop driver now, it will stop the receiver job...
        logger.debug { "Engine - Main: Stopping driver" }
        engineConfig.driver.stop()
        logger.debug { "Engine - Main: Driver stopped" }

        // Wait for receiver job
        receiverJob.join()
    }

    private suspend fun dispatcherJob() {
        logger.debug { "$LOG_PFX_DISPATCHER: started" }
        var isActive = true
        while (isActive) {

            when (val event = dispatchChannel.receive()) {
                is EngineEventAbort -> {                    // Special, ABORT event
                    logger.debug { "$LOG_PFX_DISPATCHER: received event: $event" }
                    sendChannel.send(event)
                    isActive = false
                }
                is EngineEventFrameReceived -> {            // Frame received from network
                    when (event.frame) {
                        is FrameState -> {                  // Status frame received
                            if (event.frame.predecessor == null) {
                                logger.debug { "$LOG_PFX_DISPATCHER: status frame without predecessor received ${event.frame}, forwarding to sender job - it's probably waiting for this frame" }
                                sendChannel.send(event)
                            } else {
                                if (networkInfo.isFrameWaitingForResult(event.frame)) {
                                    // Predecessor of the status frame is waiting for result
                                    logger.debug { "$LOG_PFX_DISPATCHER: status frame received ${event.frame.toStringWithPredecessor()}, callback suspended, waiting for result" }
                                } else {
                                    // Predecessor is not waiting for
                                    logger.debug { "$LOG_PFX_DISPATCHER: status frame received ${event.frame.toStringWithPredecessor()}, calling callback" }
                                }
                            }
                        }
                        is FrameSOF -> {                    // Data frame received
                            val cbKey = null//event.frame.getFunctionCallbackKey()
                            val frame = cbKey?.let {
                                val predecessor = null//networkInfo.dequeueCallbackKey(cbKey)
                                /*if (predecessor != null)
                                    event.frame.withPredecessor(predecessor)
                                else*/
                                event.frame
                            } ?: event.frame

                            if (frame.predecessor == null) {
                                logger.debug { "$LOG_PFX_DISPATCHER: frame received ${event.frame}, sending ACK" }
                            } else {
                                logger.debug { "$LOG_PFX_DISPATCHER: frame received $frame, as result for ${frame.predecessor}, sending ACK" }
                            }

                            sendChannel.sendFrame(FrameACK(network, predecessor = frame))
                        }
                    }
                }
                is EngineEventFrameSend -> {                 // Frame send request
                    logger.debug { "$LOG_PFX_DISPATCHER: frame send request ${event.frame}, sending" }
                    sendChannel.send(event)
                }
            }
        }
        logger.debug { "$LOG_PFX_DISPATCHER: stopped" }
    }


    private suspend fun sendJob() {
        logger.debug { "$LOG_PFX_SENDER: started" }

        var isActive = true
        while (isActive) {

            when (val event = sendChannel.receive()) {
                is EngineEventAbort -> {
                    logger.debug { "$LOG_PFX_SENDER: received event: $event" }
                    isActive = false
                }
                is EngineEventFrameSend -> {
                    val resultFrame = networkInfo.handleFrameEnqueue(event.frame) {
                        val timeouts = it.sendTimeouts
                        var result: Frame = it
                        var resultAwaited = false
                        for (timeout in timeouts.withIndex())
                            try {
                                engineConfig.driver.putFrame(it)
                                if (timeout.value > 0) {
                                    val sentDateTime = LocalDateTime.now()
                                    logger.debug { "$LOG_PFX_SENDER: frame $it sent, waiting ${timeout.value}ms for ACK (${timeout.index + 1}/${timeouts.size})" }
                                    resultAwaited = true
                                    val frameState =
                                        withTimeout(timeout.value) { sendChannel.receiveFrameState(sentDateTime) }
                                    logger.debug { "$LOG_PFX_SENDER: Status received: $frameState" }
                                    if (frameState is FrameACK) {
                                        // ACK received before timeout, so break the loop
                                        result = frameState.withPredecessor(it)
                                        break
                                    }
                                } else {
                                    logger.debug { "$LOG_PFX_SENDER: frame $it sent" }
                                    break
                                }
                            } catch (e: TimeoutCancellationException) {
                                // Catch timeout, and try again (if was not the last iteration)
                                logger.debug { "$LOG_PFX_SENDER: Timeout" }
                            }
                        // If result was awaited & no result received create dummy frame
                        if (resultAwaited && result == it) {
                            FrameNUL(network, it)
                        } else {
                            result
                        }
                    } // end of handleFrameSerialization

                    // If the result frame is different than the sent frame
                    // then inform the dispatcher about this new frame
                    if (resultFrame != event.frame)
                        dispatchChannel.receivedFrame(resultFrame)
                }
                is EngineEventFrameReceived -> {
                    logger.debug { "$LOG_PFX_SENDER: frame ${event.frame} received, probably too late. Ignoring." }
                }
                else -> {
                    logger.debug { "$LOG_PFX_SENDER: unknown event received $event" }
                }
            }
            delay(10)
        }
        logger.debug { "$LOG_PFX_SENDER: stopped" }
    }

    private suspend fun receiveJob() {
        logger.debug { "$LOG_PFX_RECEIVER: started" }

        while (engineConfig.driver.started) {
            val frame = engineConfig.driver.getFrame(network)
            if (frame != null) {
                logger.debug("$LOG_PFX_RECEIVER: frame received $frame")
                dispatchChannel.receivedFrame(frame)
            }
        }

        logger.debug { "$LOG_PFX_RECEIVER: job stopped (because driver is inactive)" }
    }

}