package com.oxyggen.qzw.engine

import com.oxyggen.qzw.engine.channel.EnginePriorityChannel
import com.oxyggen.qzw.engine.config.EngineConfig
import com.oxyggen.qzw.engine.event.EngineEventAbort
import com.oxyggen.qzw.engine.event.EngineEventFrameReceived
import com.oxyggen.qzw.engine.event.EngineEventFrameSend
import com.oxyggen.qzw.transport.frame.*
import com.oxyggen.qzw.engine.network.NetworkInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class Engine(val engineConfig: EngineConfig, val coroutineScope: CoroutineScope = GlobalScope) : Logging {

    // Create dispatcher Channel (this is the entry point for all events)
    private val dispatchChannel = EnginePriorityChannel()

    // Create send Channel
    private val sendChannel = EnginePriorityChannel()

    private var executionJob: Job? = null
    private val loopResultMutex = Mutex()
    private val networkInfo = NetworkInfo()

    fun start() = coroutineScope.launch { startWithLock() }

    fun stop(gently: Boolean = true) = coroutineScope.launch { stopWithLock(gently) }

    fun sendFrame(frame: Frame) {
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
        logger.debug { "Engine - Dispatcher: started" }
        var isActive = true
        while (isActive) {
            val event = dispatchChannel.receive()

            when (event) {
                is EngineEventAbort -> {
                    logger.debug { "Engine - Dispatcher: received event: $event" }
                    sendChannel.send(event)
                    isActive = false
                }
                is EngineEventFrameReceived -> {
                    when (event.frame) {
                        is FrameState -> {
                            if (event.frame.predecessor == null) {
                                logger.debug { "Engine - Dispatcher: status frame without predecessor received ${event.frame}, informing sender job - it's probably waiting for this frame" }
                                sendChannel.send(event)
                            } else {
                                logger.debug { "Engine - Dispatcher: status frame received ${event.frame.toStringWithPredecessor()}" }
                                // TODO: Now what?

                            }
                        }
                        is FrameSOF -> {
                            logger.debug { "Engine - Dispatcher: frame received ${event.frame}, sending ACK" }
                            sendChannel.sendFrame(FrameACK(predecessor = event.frame))
                        }
                    }
                }
                is EngineEventFrameSend -> {
                    logger.debug { "Engine - Dispatcher: frame send request ${event.frame}, sending" }
                    sendChannel.send(event)
                }
            }
        }
        logger.debug { "Engine - Dispatcher: stopped" }
    }


    private suspend fun sendJob() {
        logger.debug { "Engine - Sender: started" }

        var isActive = true
        while (isActive) {
            val event = sendChannel.receive()

            when (event) {
                is EngineEventAbort -> {
                    logger.debug { "Engine - Sender: received event: $event" }
                    isActive = false
                }
                is EngineEventFrameSend -> {
                    val timeouts = event.frame.sendTimeouts
                    for (timeout in timeouts.withIndex())
                        try {
                            engineConfig.driver.putFrame(event.frame, networkInfo)
                            if (timeout.value > 0) {
                                val sentDateTime = LocalDateTime.now()
                                logger.debug { "Engine - Sender: frame ${event.frame} sent, waiting ${timeout.value}ms for ACK (${timeout.index + 1}/${timeouts.size})" }
                                val frameState =
                                    withTimeout(timeout.value) { sendChannel.receiveFrameState(sentDateTime) }
                                logger.debug { "Engine - Sender: Status received: $frameState" }
                                if (frameState is FrameACK) {
                                    dispatchChannel.receivedFrame(frameState.withPredecessor(event.frame))
                                    break
                                }
                            } else {
                                logger.debug { "Engine - Sender: frame ${event.frame} sent" }
                                break
                            }
                        } catch (e: TimeoutCancellationException) {
                            logger.debug { "Engine - Sender: Timeout" }
                        }
                }
                is EngineEventFrameReceived -> {
                    logger.debug { "Engine - Sender: frame ${event.frame} received, probably too late. Ignoring." }
                }
                else -> {
                    logger.debug { "Engine - Sender: unknown event received $event" }
                }
            }
            delay(10)
        }
        logger.debug { "Engine - Sender: stopped" }
    }

    private suspend fun receiveJob() {
        logger.debug { "Engine - Receiver: started" }

        while (engineConfig.driver.started) {
            val frame = engineConfig.driver.getFrame(networkInfo)
            if (frame != null) {
                logger.debug("Engine - Receiver: frame received $frame")
                dispatchChannel.receivedFrame(frame)
            }
        }

        logger.debug { "Engine - Receiver: job stopped (because driver is inactive)" }
    }

}