package com.oxyggen.qzw.engine

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.framePrioritySelect
import com.oxyggen.qzw.engine.config.EngineConfig
import com.oxyggen.qzw.engine.exception.EngineStandardStopException
import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.scheduler.NetworkScheduler
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.frame.*
import com.oxyggen.qzw.transport.function.Function
import com.oxyggen.qzw.types.FrameResultCallback
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalUnsignedTypes::class)
class Engine(val engineConfig: EngineConfig) : Logging {

    companion object {
        const val LOG_PFX_SENDER = "\uD83D\uDCE1 => \uD83D\uDCF1"
        const val LOG_PFX_RECEIVER = "\uD83D\uDCE1 <= \uD83D\uDCF1"
    }

    private val duplexChannelSW = FrameDuplexPriorityChannel("Software", "NwSch/SW")
    private val duplexChannelZW = FrameDuplexPriorityChannel("Driver", "NwSch/ZW")

    private data class FrameCallbackPair(val frame: Frame, val callback: FrameResultCallback)

    private val genericCallbacks = mutableSetOf<FrameResultCallback>()
    private val frameCallbacks = mutableListOf<FrameCallbackPair>()

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

    fun registerGenericCallback(callback: FrameResultCallback) = genericCallbacks.add(callback)

    fun deregisterGenericCallback(callback: FrameResultCallback) = genericCallbacks.remove(callback)

    fun send(frame: Frame, callback: FrameResultCallback? = null) {
        epSW.offer(frame)
        if (callback != null) frameCallbacks += FrameCallbackPair(frame, callback)
    }

    fun sendFunction(function: Function, callback: FrameResultCallback? = null): FrameSOF {
        val frame = function.getFrame(network)
        send(frame, callback)
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

        // Network scheduler -> Driver
        val driverSenderJob = coroutineScope.launch(Dispatchers.IO) { driverSendJob() }

        // Driver -> Network scheduler
        val driverReceiverJob = coroutineScope.launch(Dispatchers.IO) { driverReceiveJob() }

        // Network scheduler -> Software callback
        val resultDispatcherJob = coroutineScope.launch { resultDispatcherJob() }

        // Network scheduler
        val networkSchedulerJob = coroutineScope.launch { networkScheduler.start(this) }

        // Wait for finish
        joinAll(driverSenderJob, driverReceiverJob, resultDispatcherJob, networkSchedulerJob)
    }

    private suspend fun resultDispatcherJob() {
        try {
            logger.info { "Result distributor started" }

            while (true) {
                val result = framePrioritySelect(epSW)
                val cb = frameCallbacks.find { it.frame.isPredecessorOf(result.frame) }
                if (cb != null) {
                    cb.callback.invoke(result.frame)
                } else {
                    genericCallbacks.forEach {
                        it.invoke(result.frame)
                    }
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Result distributor stopped" }
        }
    }


    private suspend fun driverSendJob() {
        try {
            logger.info { "Driver sender started" }

            while (true) {
                val frame = epZW.receive()
                engineConfig.driver.putFrame(frame)
                frame.setSent()
                logger.debug("$LOG_PFX_SENDER: Frame sent to driver. Frame: $frame")
                delay(50)
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Driver sender stopped" }
        }

    }

    private suspend fun driverReceiveJob() {
        try {
            logger.info { "Driver receiver started" }

            while (true) {
                val frame = engineConfig.driver.getFrame(network)
                frame?.let {
                    logger.debug("$LOG_PFX_RECEIVER: Frame received from driver. Frame: $it")
                    epZW.send(it)
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Driver receiver stopped" }
        }
    }

}