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

    private data class NodeSchedulerExt(
        val nodeScheduler: NodeScheduler,
        val epSW: FrameDuplexPriorityChannelEndpoint,
        val epZW: FrameDuplexPriorityChannelEndpoint
    )

    fun getLocalNodeId() = NodeID(1)

    private val waitingFrameQueue = WaitingFrameQueue()

    private var loopJob: Job? = null
    private val loopJobMutex = Mutex()

    private var nodeSchedulerExt = mutableMapOf<Node, NodeSchedulerExt>()

    private var frameSendTimeouts = generateSequence(200L) { it + 1000 }.take(4).toList()

    private suspend fun createNodeSchedulerExt(node: Node, coroutineScope: CoroutineScope): NodeSchedulerExt {
        val endpointBPrefix = if (node == Node.SERIAL_API) "SerialApiSch" else "NoSch[${node.nodeID}]"

        val duplexChannelSW = FrameDuplexPriorityChannel("NwSch/SW", "$endpointBPrefix/SW")
        val duplexChannelZW = FrameDuplexPriorityChannel("NwSch/ZW", "$endpointBPrefix/ZW")

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
    private suspend fun routeFromDriverToNodeScheduler(
        epSrc: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {

        when (frame) {
            is FrameState -> {              // ACK/SOF/NAK from Z-Wave, but we are not waiting for it => ignore
                logger.debug { "Status frame received without predecessor, ignoring. Frame: $frame" }
            }
            is FrameSOF -> {                // Data frame received, determine destination node
                // Try to dequeue waiting frame
                // frameWaiting is returned when frame was waiting in queue
                // frameReceived is returned always. If waiting frame was found
                //               received frame contains it as predecessor
                val (frameWaiting, frameReceived) = waitingFrameQueue.dequeue(frame)

                val node = frameReceived.getNode()

                if (node != null) {
                    val nodeSchedulerExt = getNodeSchedulerExt(node, coroutineScope)
                    val epDst = nodeSchedulerExt.epZW

                    val frameWithACK = FrameACK(network,frameReceived)

                    // Send ACK back to ZW driver
                    epZW.send(frameWithACK)

                    if (frameWaiting != null) {
                        logger.debug { "$epSrc ⟹ ${epDst.remoteEndpoint} frame $frameReceived (is answer for predecessor ${frameWaiting.toStringWithPredecessor()})" }
                    } else {
                        logger.debug { "$epSrc ⟹ ${epDst.remoteEndpoint} frame $frameReceived" }
                    }

                    // Send the frame sequence to node scheduler
                    epDst.send(frameWithACK)
                } else {
                    logger.error { "Frame received from $epSrc, but node was not determined, so frame ignored! Frame: $frame" }
                }
            }
        }
    }

    // Z-Wave -> Node: Frame came from ZW interface
    private suspend fun routeFromSoftwareToNodeScheduler(
        epSrc: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {

        when (frame) {
            is FrameState -> {              // ACK/SOF/NAK from Z-Wave, but we are not waiting for it => ignore
                logger.debug { "Status frame received from software. Ignoring." }
            }
            is FrameSOF -> {                // Data frame received, determine destination node
                val node = frame.getNode() ?: Node(NodeID.SERIAL_API)

                val nodeSchedulerExt = getNodeSchedulerExt(node, coroutineScope)
                val epDst = nodeSchedulerExt.epSW
                logger.debug { "$epSrc -> ${epDst.remoteEndpoint} frame $frame" }
                epDst.send(frame)
            }
        }
    }

    // Node -> Z-Wave: Handle frame from node to Z-Wave driver
    private suspend fun routeFromNodeSchedulerToDriver(
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
                            logger.debug { "Frame sent, waiting ${timeout.value}ms for ACK (${timeout.index + 1}/${frameSendTimeouts.size}). Frame: $frame" }
                            val frameState =
                                withTimeout(timeout.value) { epZW.receiveFrameState(frame) }
                            logger.debug { "Status received: $frameState" }
                            if (frameState is FrameACK) {       // ACK received before timeout, so break the loop
                                result = frameState
                                break
                            }
                        } else {
                            logger.debug { "Frame sent. Frame: $frame" }
                            break
                        }
                    } catch (e: TimeoutCancellationException) {
                        // Catch timeout, and try again (if was not the last iteration)
                        logger.debug { "Timeout when waiting for ACK" }
                    }

                if (result != null) {
                    if (result.isAwaitingResult()) {
                        logger.debug { "Frame is waiting for result, so adding it to waiting frame queue. Frame: ${result.toStringWithPredecessor()} " }
                        waitingFrameQueue.add(result)
                    } else {
                        logger.debug { "Frame is not waiting for result, sending back to ${nodeEpZW.remoteEndpoint}. Frame: ${frame.toStringWithPredecessor()}" }
                        nodeEpZW.send(result)
                    }
                } else {
                    // If result was awaited & no result received create dummy frame and send back to node
                    nodeEpZW.send(FrameNUL(network, frame))
                }
            }
        }
    }

    // Node -> Software: Handle frame from node to software
    private suspend fun routeFromNodeSchedulerToSoftware(
        nodeEpSW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        epSW.send(frame)
    }

    private suspend fun loop(coroutineScope: CoroutineScope) {
        try {
            logger.info { "Scheduler started" }
            while (true) {

                // Create list of endpoints from schedulers
                val nodeZWEps = nodeSchedulerExt.map { it.value.epZW }
                val nodeSWEps = nodeSchedulerExt.map { it.value.epSW }

                // Create endpoint list for select
                val eps = mutableListOf<FrameDuplexPriorityChannelEndpoint>()

                // Add all endpoints
                eps += epZW
                eps += epSW
                if (waitingFrameQueue.isEmpty()) eps += nodeZWEps
                eps += nodeSWEps

                // Select highest priority frame
                val (ep, frame) = framePrioritySelect(*eps.toTypedArray())

                // Collect timed out waiting frames
                val timedOut = waitingFrameQueue.collectTimedOut()
                timedOut.forEach {
                    logger.warn("Frame timed out ${it.toStringWithPredecessor()}")
                }

                // Determine route
                when (ep) {
                    epZW -> routeFromDriverToNodeScheduler(ep, frame, coroutineScope)
                    epSW -> routeFromSoftwareToNodeScheduler(ep, frame, coroutineScope)
                    in nodeZWEps -> routeFromNodeSchedulerToDriver(ep, frame, coroutineScope)
                    in nodeSWEps -> routeFromNodeSchedulerToSoftware(ep, frame, coroutineScope)
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Scheduler stopped" }
        }
    }
}