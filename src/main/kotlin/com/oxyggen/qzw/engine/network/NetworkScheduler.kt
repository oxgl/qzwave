package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel.Connection
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannelEndpoint
import com.oxyggen.qzw.engine.channel.framePrioritySelect
import com.oxyggen.qzw.transport.frame.*
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging


class NetworkScheduler(
    val network: Network = Network(),
    val epSW: FrameDuplexPriorityChannelEndpoint,
    val epZW: FrameDuplexPriorityChannelEndpoint
) : Logging {

    companion object {
        const val LOG_PFX = "Network scheduler"
    }

    private data class NodeSchedulerExt(
        val nodeScheduler: NodeScheduler,
        val epSW: FrameDuplexPriorityChannelEndpoint,
        val epZW: FrameDuplexPriorityChannelEndpoint
    )

    fun getLocalNodeId() = NodeID(1)

    private var loopJob: Job? = null
    private val loopJobMutex = Mutex()

    private var nodeSchedulerExt = mutableMapOf<Node, NodeSchedulerExt>()

    private var frameSendTimeouts = generateSequence(200L) { it + 1000 }.take(4).toList()

    private fun createNodeSchedulerExt(node: Node, coroutineScope: CoroutineScope): NodeSchedulerExt {
        val duplexChannelSW = FrameDuplexPriorityChannel(Connection.SW)
        val duplexChannelZW = FrameDuplexPriorityChannel(Connection.ZW)

        val nodeScheduler = NodeScheduler(this, node, duplexChannelSW.endpointB, duplexChannelZW.endpointB)


        return NodeSchedulerExt(
            NodeScheduler(this, node, duplexChannelSW.endpointB, duplexChannelZW.endpointB),
            duplexChannelSW.endpointA, duplexChannelZW.endpointA
        )
    }

    private fun getNodeSchedulerExt(node: Node, coroutineScope: CoroutineScope): NodeSchedulerExt =
        nodeSchedulerExt.getOrPut(node, { createNodeSchedulerExt(node, coroutineScope) })

    suspend fun start(coroutineScope: CoroutineScope) = loopJobMutex.withLock {
        if (loopJob?.isActive?.not() == true)
            loopJob = coroutineScope.launch { loop(this) }
    }

    suspend fun stop() = loopJobMutex.withLock {
        loopJob?.cancelAndJoin()
        loopJob = null
    }

    // Z-Wave -> Node: Frame came from ZW interface
    private suspend fun handleFrameFromDriver(
        epZW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        when (frame) {
            is FrameState -> {              // ACK/SOF/NAK from Z-Wave, but we are not waiting for it => ignore
                logger.debug { "$LOG_PFX: frame $frame received, ignoring" }
            }
            is FrameSOF -> {                // Data frame received, determine destination node
                val node = frame.getNode()

                if (node != null) {       // Node ID found => send frame to node scheduler
                    logger.debug { "$LOG_PFX: frame $frame received, from node $node" }
                    val nodeSchedulerExt = getNodeSchedulerExt(node, coroutineScope)
                    nodeSchedulerExt.epZW.send(frame)
                } else {                    // Node ID not found => ?
                    logger.debug { "$LOG_PFX: frame $frame received, without source node => handle it" }

                }
            }
        }
    }

    // Software -> Node: Frame came from software interface
    private suspend fun handleFrameFromSoftware(
        epZW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        when (frame) {
            is FrameState -> {              // ACK/SOF/NAK from Z-Wave, but we are not waiting for it => ignore
                logger.debug { "$LOG_PFX: frame $frame received, ignoring" }
            }
            is FrameSOF -> {                // Data frame received, determine destination node
                val node = frame.getNode()

                if (node != null) {       // Node ID found => send frame to node scheduler
                    logger.debug { "$LOG_PFX: frame $frame received, from node $node" }
                    val nodeSchedulerExt = getNodeSchedulerExt(node, coroutineScope)
                    nodeSchedulerExt.epSW.send(frame)
                } else {                    // Node ID not found => ?
                    logger.debug { "$LOG_PFX: frame $frame received, without source node => handle it" }

                }
            }
        }
    }


    // Node -> Z-Wave: Handle frame from node to Z-Wave driver
    private suspend fun handleFrameFromNodeSchedulerToDriver(
        nodeEpZW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        when (frame) {
            is FrameState -> {
                // ACK/SOF/NAK from NodeScheduler => send to ZW and continue
                epZW.send(frame)
            }
            is FrameSOF -> {
                // Data frame from NodeScheduler => send to ZW and wait for timeout
                var result: FrameState? = null
                for (timeout in frameSendTimeouts.withIndex())
                    try {
                        // Send out this frame to ZW interface
                        epZW.send(frame)
                        if (timeout.value > 0) {
                            logger.debug { "$LOG_PFX: frame $frame sent, waiting ${timeout.value}ms for ACK (${timeout.index + 1}/${frameSendTimeouts.size})" }
                            val frameState =
                                withTimeout(timeout.value) { epZW.receiveFrameState(frame) }
                            logger.debug { "$LOG_PFX: Status received: $frameState" }
                            if (frameState is FrameACK) {       // ACK received before timeout, so break the loop
                                result = frameState
                                break
                            }
                        } else {
                            logger.debug { "$LOG_PFX: frame $frame sent" }
                            break
                        }
                    } catch (e: TimeoutCancellationException) {
                        // Catch timeout, and try again (if was not the last iteration)
                        logger.debug { "$LOG_PFX: Timeout" }
                    }

                if (result != null) {
                    // Result received => send back to node
                    nodeEpZW.send(result)
                } else {
                    // If result was awaited & no result received create dummy frame and send back to node
                    nodeEpZW.send(FrameNUL(network, frame))
                }
            }
        }
    }

    // Node -> Software: Handle frame from node to software
    private suspend fun handleFrameFromNodeSchedulerToSoftware(
        nodeEpSW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        epSW.send(frame)
    }

    private suspend fun loop(coroutineScope: CoroutineScope) {
        try {
            logger.debug { "Network scheduler: started" }
            val nodeZwEps = nodeSchedulerExt.map { it.value.epZW }.toTypedArray()
            val nodeSwEps = nodeSchedulerExt.map { it.value.epSW }.toTypedArray()
            while (true) {
                val (ep, frame) = framePrioritySelect(epZW, epSW, *nodeZwEps, *nodeSwEps)

                when (ep) {
                    epZW -> handleFrameFromDriver(ep, frame, coroutineScope)
                    epSW -> handleFrameFromSoftware(ep, frame, coroutineScope)
                    in nodeZwEps -> handleFrameFromNodeSchedulerToDriver(ep, frame, coroutineScope)
                    in nodeSwEps -> handleFrameFromNodeSchedulerToSoftware(ep, frame, coroutineScope)
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "Network scheduler: stopped" }
        }
    }
}