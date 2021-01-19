package com.oxyggen.qzw.engine

import com.oxyggen.qzw.command.CCSecurity
import com.oxyggen.qzw.frame.Frame
import com.oxyggen.qzw.frame.FrameACK
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.FunctionApplicationCommandHandler
import com.oxyggen.qzw.node.NetworkInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class Engine(val engineConfig: EngineConfig, val coroutineScope: CoroutineScope = GlobalScope) : Logging {

    private val lowPrioSendChannel = Channel<EngineEvent>(Channel.UNLIMITED)
    private val highPrioSendChannel = Channel<EngineEvent>(Channel.UNLIMITED)
    private val receiveChannel = Channel<EngineEvent>(Channel.UNLIMITED)

    private enum class Status {
        STARTED, STOPPED, STOPPING
    }

    private var executionJob: Job? = null
    private val loopResultMutex = Mutex()
    private val networkInfo = NetworkInfo()

    fun start() = coroutineScope.launch { startWithLock() }

    fun stop(gently: Boolean = true) = coroutineScope.launch { stopWithLock(gently) }

    fun sendFrame(frame: Frame) =
        coroutineScope.launch {
            if (started) lowPrioSendChannel.send(EngineEvent(EngineEvent.Type.SEND_FRAME, frame))
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
                    lowPrioSendChannel.send(EngineEvent.EVENT_ABORT)
                    receiveChannel.send(EngineEvent.EVENT_ABORT)
                } else {
                    executionJob!!.cancel()
                    engineConfig.driver.stop()
                }
        }
    }

    private suspend fun executeJobs() {
        if (!engineConfig.driver.start()) {
            logger.error { "Engine: Unable to start driver!" }
            return
        } else {
            logger.debug { "Engine: Driver started" }
        }
        val senderJob = coroutineScope.launch { sendJob() }
        val receiverJob = coroutineScope.launch { receiveJob() }

        senderJob.join()
        receiverJob.join()

        engineConfig.driver.stop()
        logger.debug { "Engine: Driver stopped" }
    }

    private suspend fun sendJob() {
        logger.debug { "Engine: Send job started" }

        var isActive = true
        while (isActive) {
            val event = select<EngineEvent> {
                highPrioSendChannel.onReceive { it }
                lowPrioSendChannel.onReceive { it }
            }
            logger.debug { "Engine: Send job received event: $event" }
            when (event.type) {
                EngineEvent.Type.ABORT -> isActive = false
                EngineEvent.Type.SEND_FRAME -> when (event.data) {
                    is Frame -> {
                        engineConfig.driver.putFrame(event.data, networkInfo)
                    }
                }
            }
            delay(10)
        }
        logger.debug { "Engine: Send job stopped" }
    }


    private suspend fun receiveJob() {
        logger.debug { "Engine: Receive job started" }

        var isActive = true
        while (isActive) {
            if (engineConfig.driver.dataAvailable() > 0) {
                val frame = engineConfig.driver.getFrame(networkInfo)
                logger.debug("Engine: Frame received ${frame}")

                if (frame != null) {
                    highPrioSendChannel.send(EngineEvent(EngineEvent.Type.SEND_FRAME, FrameACK()))

                    /*    if (frame is FrameSOF) {
                            if (frame.function is FunctionApplicationCommandHandler.Request) {
                                if (frame.function.command is CCSecurity.NonceGet) {
                                    val nonceBytes = ByteArray(8)
                                    Random(12).nextBytes(nonceBytes)
                                    sendFrame(
                                        CCSecurity.NonceReport(nonceBytes).getSendDataFrame(frame.function.sourceNodeID)
                                    )
                                }
                            }
                        }*/

                }
            }

            if (!receiveChannel.isEmpty) {
                val event = receiveChannel.receive()
                logger.debug { "Engine: Receive job received event: $event" }
                when (event.type) {
                    EngineEvent.Type.ABORT -> isActive = false
                    else -> {
                    }
                }
            }
            delay(50)
        }

        logger.debug { "Engine: Receive job stopped" }
    }

}