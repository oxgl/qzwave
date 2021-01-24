package com.oxyggen.qzw.engine

import com.oxyggen.qzw.frame.*
import com.oxyggen.qzw.node.NetworkInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class Engine(val engineConfig: EngineConfig, val coroutineScope: CoroutineScope = GlobalScope) : Logging {

    companion object {
        const val CHANNEL_PRIO_COUNT = 3
        const val CHANNEL_PRIO_HIGH = 0
        const val CHANNEL_PRIO_NORMAL = 1
        const val CHANNEL_PRIO_LOW = 2
    }

    private fun listOfChannels(count: Int) =
        sequence<Channel<EngineEvent>> { while (true) yield(Channel(Channel.UNLIMITED)) }.take(count).toList()

    // Transmit control channel
    private val txStateChannel = Channel<EngineEvent>(Channel.UNLIMITED)

    // Create dispatcher Channels (this is the entry point for all events)
    private val dispatcherChannels = listOfChannels(CHANNEL_PRIO_COUNT)

    // Create send Channels
    private val sendChannels = listOfChannels(CHANNEL_PRIO_COUNT)

    private enum class Status {
        STARTED, STOPPED, STOPPING
    }

    private var executionJob: Job? = null
    private val loopResultMutex = Mutex()
    private val networkInfo = NetworkInfo()

    fun start() = coroutineScope.launch { startWithLock() }

    fun stop(gently: Boolean = true) = coroutineScope.launch { stopWithLock(gently) }

    fun sendFrame(frame: Frame) {
        coroutineScope.launch {
            dispatchFrameSend(frame)
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
                    dispatch(EngineEvent.EVENT_ABORT)
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

    private suspend fun dispatch(event: EngineEvent, priority: Int = CHANNEL_PRIO_NORMAL) =
        dispatcherChannels[priority].send(event)

    private suspend fun dispatchFrameSend(frame: Frame, priority: Int = CHANNEL_PRIO_NORMAL) =
        dispatch(EngineEvent(EngineEvent.Type.FRAME_SEND, frame), priority)

    private suspend fun dispatchFrameReceived(frame: Frame, priority: Int = CHANNEL_PRIO_NORMAL) =
        dispatch(EngineEvent(EngineEvent.Type.FRAME_RECEIVED, frame), priority)


    private suspend fun dispatcherJob() {
        logger.debug { "Engine - Dispatcher: started" }
        var isActive = true
        while (isActive) {
            val event = select<EngineEvent> {
                for (dispatcherChannel in dispatcherChannels) {
                    dispatcherChannel.onReceive { it }
                }
            }

            when (event.type) {
                EngineEvent.Type.ABORT -> {
                    logger.debug { "Engine - Dispatcher: received event: $event" }
                    sendChannels[CHANNEL_PRIO_NORMAL].send(EngineEvent.EVENT_ABORT)
                    isActive = false
                }
                EngineEvent.Type.FRAME_SEND -> {
                    sendChannels[CHANNEL_PRIO_NORMAL].send(event)
                }
                EngineEvent.Type.FRAME_RECEIVED -> when (event.data) {
                    is FrameState -> {
                        logger.debug { "Engine - Dispatcher: control frame received ${event.data}, informing sender job" }
                        txStateChannel.send(event)
                    }
                    is FrameSOF -> {
                        logger.debug { "Engine - Dispatcher: frame received ${event.data}, sending ACK" }
                        sendChannels[CHANNEL_PRIO_HIGH].send(EngineEvent(EngineEvent.Type.FRAME_SEND, FrameACK()))
                    }
                }
            }
        }
        logger.debug { "Engine - Dispatcher: stopped" }
    }


    private suspend fun sendJob() {
        logger.debug { "Engine - Sender: started" }

        var isActive = true
        while (isActive) {
            val event = select<EngineEvent> {
                for (sendChannel in sendChannels) {
                    sendChannel.onReceive { it }
                }
            }
            when (event.type) {
                EngineEvent.Type.ABORT -> {
                    logger.debug { "Engine - Sender: received event: $event" }
                    isActive = false
                }
                EngineEvent.Type.FRAME_SEND -> when (event.data) {
                    is Frame -> {
                        val timeouts = event.data.sendTimeouts
                        for (timeout in timeouts.withIndex())
                            try {
                                engineConfig.driver.putFrame(event.data, networkInfo)
                                if (timeout.value > 0) {
                                    val sentDateTime = LocalDateTime.now()
                                    logger.debug { "Engine - Sender: frame ${event.data} sent, waiting ${timeout}ms for ACK (${timeout.index + 1}/${timeouts.size})" }
                                    val frameState = withTimeout(timeout.value) {
                                        var stateEvent: EngineEvent?
                                        while (true) {
                                            stateEvent = txStateChannel.receive()
                                            if (stateEvent.created.isAfter(sentDateTime)) {
                                                break
                                            }
                                        }
                                        (stateEvent?.data as FrameState).withPredecessor(event.data)
                                    }
                                    logger.debug { "Engine - Sender: Status received: $frameState" }
                                    if (frameState is FrameACK) {
                                        break
                                    }
                                } else {
                                    logger.debug { "Engine - Sender: frame ${event.data} sent" }
                                    break
                                }
                            } catch (e: TimeoutCancellationException) {
                                logger.debug { "Engine - Sender: Timeout" }
                            }
                    }
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
                when (frame) {
                    is FrameState -> dispatchFrameReceived(frame, CHANNEL_PRIO_HIGH)
                    else -> dispatchFrameReceived(frame, CHANNEL_PRIO_NORMAL)
                }
            }
        }

        logger.debug { "Engine - Receiver: job stopped (because driver is inactive)" }
    }

}