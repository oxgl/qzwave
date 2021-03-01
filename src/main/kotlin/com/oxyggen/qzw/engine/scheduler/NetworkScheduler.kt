package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannelEndpoint
import com.oxyggen.qzw.engine.channel.framePrioritySelect
import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
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

    private val framesAwaitingResult = mutableListOf<Frame>()

    private var loopJob: Job? = null
    private val loopJobMutex = Mutex()

    private var nodeSchedulerExt = mutableMapOf<Node, NodeSchedulerExt>()

    private var frameSendTimeouts = generateSequence(200L) { it + 1000 }.take(4).toList()

    private suspend fun createNodeSchedulerExt(node: Node, coroutineScope: CoroutineScope): NodeSchedulerExt {
        val duplexChannelSW = FrameDuplexPriorityChannel("SW/NwSch", "SW/NoSch")
        val duplexChannelZW = FrameDuplexPriorityChannel("ZW/NwSch", "ZW/NoSch")

        val nodeScheduler = NodeScheduler(this, node, duplexChannelSW.endpointB, duplexChannelZW.endpointB)
        nodeScheduler.start(coroutineScope)

        return NodeSchedulerExt(
            nodeScheduler,
            duplexChannelSW.endpointA, duplexChannelZW.endpointA
        )
    }

    private suspend fun getNodeSchedulerExt(node: Node, coroutineScope: CoroutineScope): NodeSchedulerExt =
        nodeSchedulerExt.getOrPut(node, { createNodeSchedulerExt(node, coroutineScope) })

    suspend fun start(coroutineScope: CoroutineScope) = loopJobMutex.withLock {
        if (loopJob?.isActive != true)
            loopJob = coroutineScope.launch { loop(this) }
    }

    suspend fun stop() = loopJobMutex.withLock {
        loopJob?.cancelAndJoin()
        loopJob = null
    }

    // Z-Wave -> Node: Frame came from ZW interface
    private suspend fun routeFrameToNodeScheduler(
        ep: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {

        when (frame) {
            is FrameState -> {              // ACK/SOF/NAK from Z-Wave, but we are not waiting for it => ignore
                logger.debug { "$LOG_PFX: Status frame $frame received without predecessor, ignoring" }
            }
            is FrameSOF -> {                // Data frame received, determine destination node
                val waitingFrame = framesAwaitingResult.find { it.isAwaitedResult(frame) }
                val receivedFrame = if (waitingFrame != null) {
                    framesAwaitingResult.remove(waitingFrame)
                    frame.withPredecessor(waitingFrame) ?: frame
                } else {
                    frame
                }
                val node = receivedFrame.getNode()

                if (node != null) {
                    if (waitingFrame != null) {
                        logger.debug { "$LOG_PFX: frame $frame received from $ep, frame $waitingFrame was waiting for this result, sending to node scheduler $node" }
                    } else {
                        logger.debug { "$LOG_PFX: frame $frame received from $ep, sending to node scheduler $node" }
                    }
                    val nodeSchedulerExt = getNodeSchedulerExt(node, coroutineScope)
                    nodeSchedulerExt.epZW.send(frame)
                } else {
                    logger.error { "$LOG_PFX: frame $frame received, but node was not determined, so ignore it!" }
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
                    if (result.isAwaitingResult())
                        framesAwaitingResult += result
                    else
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
            logger.debug { "$LOG_PFX: started" }
            while (true) {
                val eps = mutableListOf<FrameDuplexPriorityChannelEndpoint>()

                val nodeZWEps = nodeSchedulerExt.map { it.value.epZW }
                val nodeSWEps = nodeSchedulerExt.map { it.value.epSW }

                eps += epZW
                eps += epSW
                eps += nodeZWEps
                eps += nodeSWEps

                val (ep, frame) = framePrioritySelect(*eps.toTypedArray())

                when (ep) {
                    epZW, epSW -> routeFrameToNodeScheduler(ep, frame, coroutineScope)
                    in nodeZWEps -> handleFrameFromNodeSchedulerToDriver(ep, frame, coroutineScope)
                    in nodeSWEps -> handleFrameFromNodeSchedulerToSoftware(ep, frame, coroutineScope)
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "$LOG_PFX: stopped" }
        }
    }
}