package com.oxyggen.qzw.engine

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.config.EngineConfig
import com.oxyggen.qzw.engine.exception.EngineStandardStopException
import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.scheduler.NetworkScheduler
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.frame.*
import com.oxyggen.qzw.transport.function.Function
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalUnsignedTypes::class)
class Engine(val engineConfig: EngineConfig) : Logging {

    companion object {
        const val LOG_PFX_SENDER = "\uD83D\uDCE1\u2B08"
        const val LOG_PFX_RECEIVER = "\uD83D\uDCE1\u2B0B"
    }

    private val duplexChannelSW = FrameDuplexPriorityChannel("Software", "NwSch/SW")
    private val duplexChannelZW = FrameDuplexPriorityChannel("Driver", "NwSch/ZW")

    private val epSW = duplexChannelSW.endpointA
    private val epZW = duplexChannelZW.endpointA

    private val network = Network()

    private val networkScheduler = NetworkScheduler(network, duplexChannelSW.endpointB, duplexChannelZW.endpointB)

    private var executionJob: Job? = null
    private val executionJobMutex = Mutex()

    fun start(coroutineScope: CoroutineScope = GlobalScope) = coroutineScope.launch { startWithLock(this) }

    fun stop() = executionJob?.cancel(EngineStandardStopException())

    fun getNodeByID(nodeID: NodeID): Node = network.getNode(nodeID)

    fun getNodeByID(i: Int): Node = getNodeByID(NodeID(i))

    fun send(frame: Frame, callback: ((frame: Frame) -> Unit)? = null) {

    }

    fun sendFunction(function: Function): FrameSOF {
        val frame = function.getFrame(network)
        epSW.offer(frame)
        return frame
    }

    val started: Boolean
        get() = executionJob?.isActive ?: false

    private suspend fun startWithLock(coroutineScope: CoroutineScope) {
        executionJobMutex.withLock {
            if (!started) {
                executionJob = coroutineScope.launch { executeJobs(this) }
                executionJob?.invokeOnCompletion {
                    if (engineConfig.driver.started) {
                        logger.info { "Stopping driver" }
                        engineConfig.driver.stop()
                    }
                    logger.info { "Driver stopped" }
                }
            }
        }
    }


    private suspend fun executeJobs(coroutineScope: CoroutineScope) {
        if (!engineConfig.driver.start()) {
            logger.error { "Unable to start driver!" }
            throw CancellationException("Unable to start driver!")
        } else {
            logger.info { "Driver started" }
        }

        // Initialize network
        network.initCallbackKeys()

        // SendChannel -> Driver
        val senderJob = coroutineScope.launch(Dispatchers.IO) { sendJob() }

        // Driver -> DispatcherChannel
        val receiverJob = coroutineScope.launch(Dispatchers.IO) { receiveJob() }

        // DispatcherChannel -> SendChannel
        val networkSchedulerJob = coroutineScope.launch { networkScheduler.start(this) }

        // Wait for finish
        joinAll(senderJob, receiverJob, networkSchedulerJob)
    }


    private suspend fun sendJob() {
        try {
            logger.info { "Sender started" }

            while (true) {
                val frame = epZW.receive()
                engineConfig.driver.putFrame(frame)
                frame.setSent()
                logger.debug("$LOG_PFX_SENDER: Frame sent to driver. Frame: $frame")
                delay(10)
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Sender stopped" }
        }

    }

    private suspend fun receiveJob() {
        try {
            logger.info { "Receiver started" }

            while (true) {
                val frame = engineConfig.driver.getFrame(network)
                frame?.let {
                    logger.debug("$LOG_PFX_RECEIVER: Frame received from driver. Frame: $it")
                    epZW.send(it)
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Receiver stopped" }
        }
    }

}