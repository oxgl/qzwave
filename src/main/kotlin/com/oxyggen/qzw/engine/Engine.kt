package com.oxyggen.qzw.engine

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel.Connection
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

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalUnsignedTypes::class)
class Engine(val engineConfig: EngineConfig) : Logging {

    companion object {
        const val LOG_PFX_SENDER = "H -> ZW"
        const val LOG_PFX_RECEIVER = "H <- ZW"
        const val LOG_PFX_DISPATCHER = "<- * ->"
    }

    private val duplexChannelSW = FrameDuplexPriorityChannel(Connection.SW)
    private val duplexChannelZW = FrameDuplexPriorityChannel(Connection.ZW)

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
                        engineConfig.driver.stop()
                        logger.info { "Engine - Main: Stopping driver" }
                    }
                    logger.info { "Engine - Main: Driver stopped" }
                }
            }
        }
    }


    private suspend fun executeJobs(coroutineScope: CoroutineScope) {
        if (!engineConfig.driver.start()) {
            logger.error { "Engine - Main: Unable to start driver!" }
            throw CancellationException("Engine - Main: Unable to start driver!")
        } else {
            logger.info { "Engine - Main: Driver started" }
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
            logger.debug { "$LOG_PFX_SENDER: started" }

            while (true) {
                val frame = epZW.receive()
                engineConfig.driver.putFrame(frame)
                delay(10)
            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "$LOG_PFX_SENDER: stopped" }
        }

    }

    private suspend fun receiveJob() {
        try {
            logger.debug { "$LOG_PFX_RECEIVER: started" }

            while (true) {
                val frame = engineConfig.driver.getFrame(network)
                frame?.let {
                    logger.debug("$LOG_PFX_RECEIVER: frame received $it")
                    epZW.send(it)
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "$LOG_PFX_RECEIVER: job stopped" }
        }
    }

}