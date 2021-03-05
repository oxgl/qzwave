package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannelEndpoint
import com.oxyggen.qzw.engine.channel.framePrioritySelect
import com.oxyggen.qzw.engine.channel.framePrioritySelectWithTimeout
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

    private data class FrameSendScheduleEntry(val iteration: Int, val timeout: Long, val retransmissionWait: Long)

    // Node list synchronization channels (only dummy frames)
    private val nodeZWSync = FrameDuplexPriorityChannel("Node sync/A", "Node sync/B")
    private val nodeSWSync = FrameDuplexPriorityChannel("Node sync/A", "Node sync/B")

    // Driver endpoint for state frames
    private val epZWState =
        epZW.getPartialChannelEndpoint(
            { priority -> priority == FrameDuplexPriorityChannel.CHANNEL_PRIORITY_STATE },
            "${epZW.name}/State"
        )

    // Driver endpoint for data frames
    private val epZWData =
        epZW.getPartialChannelEndpoint(
            { priority -> priority != FrameDuplexPriorityChannel.CHANNEL_PRIORITY_STATE },
            "${epZW.name}/Data"
        )


    fun getLocalNodeId() = NodeID(1)

    private val waitingFrameQueue = WaitingFrameQueue()

    private var loopJob: Job? = null
    private val loopJobMutex = Mutex()

    private var nodeSchedulerExt = mutableMapOf<Node, NodeSchedulerExt>()


    /* Iteration:       Timeout waiting for result      Wait before retransmission
    *  1                1500                            100
    *  2                1500                            1100
    *  3                1500                            2100
    *  4                1500                            3100
    */
    private val frameSendSchedule = generateSequence(FrameSendScheduleEntry(1, 1500L, 100L)) {
        FrameSendScheduleEntry(it.iteration + 1, it.timeout, it.retransmissionWait + 1000)
    }.take(4).toList()


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

    private suspend fun getNodeSchedulerExt(node: Node, coroutineScope: CoroutineScope): NodeSchedulerExt {
        // basically getOrPut, but we need to be sure the sync frames are sent after the map is updated
        val existing = nodeSchedulerExt[node]
        if (existing != null) {
            return existing
        } else {
            val created = createNodeSchedulerExt(node, coroutineScope)
            nodeSchedulerExt[node] = created
            nodeSWSync.endpointA.send(FrameSYN(network))
            nodeZWSync.endpointA.send(FrameSYN(network))
            return created
        }
    }

    suspend fun start(coroutineScope: CoroutineScope) = loopJobMutex.withLock {
        if (loopJob?.isActive != true)
            loopJob = coroutineScope.launch {
                // Node scheduler -> Driver
                launch { scheduleFromNodeSchedulerToDriver(this) }
                // Driver -> Node scheduler
                launch { scheduleFromDriverToNodeScheduler(this) }
                // Node scheduler <-> Software
                launch { softwareAndNodeScheduler(this) }
            }
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
                if (frame.isAwaitingResult()) {
                    logger.debug { "Frame is waiting for result, so adding it to waiting frame queue. Frame: ${frame.toStringWithPredecessor()} " }
                    //waitingFrameQueue.add(result, srcEpZW)
                } else {
                    //logger.debug { "Frame is not waiting for result, sending back to ${srcEpZW.remoteEndpoint}. Frame: ${frame.toStringWithPredecessor()}" }
                    //srcEpZW.send(result)
                }
                logger.debug { "Status frame received without predecessor, ignoring. Frame: $frame" }
            }
            is FrameSOF -> {                // Data frame received, determine destination node
                // Try to dequeue waiting frame
                // frameWaiting is returned when frame was waiting in queue
                // frameReceived is returned always. If waiting frame was found
                //               received frame contains it as predecessor
                val (predecessorFrameInfo, frameReceived) = waitingFrameQueue.dequeue(frame)

                val node = frameReceived.getNode()

                if (node != null) {
                    val nodeSchedulerExt = getNodeSchedulerExt(node, coroutineScope)
                    val epDst = nodeSchedulerExt.epZW

                    val frameWithACK = FrameACK(network, frameReceived)

                    // Send ACK back to ZW driver
                    epZW.send(frameWithACK)

                    if (predecessorFrameInfo != null) {
                        logger.debug { "$epSrc ⟹ ${epDst.remoteEndpoint} frame $frameReceived (is answer for predecessor ${predecessorFrameInfo.frame.toStringWithPredecessor()})" }
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
        srcEpZW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        epZW.send(frame)
    }

    // Node -> Software: Handle frame from node to software
    private suspend fun routeFromNodeSchedulerToSoftware(
        nodeEpSW: FrameDuplexPriorityChannelEndpoint,
        frame: Frame,
        coroutineScope: CoroutineScope
    ) {
        epSW.send(frame)
    }


    /**
     * Function is scheduling the frame sending process
     */
    private suspend fun scheduleFromNodeSchedulerToDriver(coroutineScope: CoroutineScope) {
        try {
            logger.info { "Send scheduler started" }
            var nodeZWEps: Collection<FrameDuplexPriorityChannelEndpoint>? = null
            while (true) {
                // Create list of endpoints from schedulers
                if (nodeZWEps == null)
                    nodeZWEps = nodeSchedulerExt.map { it.value.epZW }

                // Calculate next timeout when frame is waiting in queue
                val nextTimeout = waitingFrameQueue.nextTimeout()

                // Select highest priority frame from Node Schedulers
                // Is timeout is reached clear waiting frames
                val result =
                    framePrioritySelectWithTimeout(nextTimeout, nodeZWSync.endpointB, *nodeZWEps.toTypedArray())

                // Collect timed out waiting frames => very important to inform node scheduler, because it's blocked...
                val timedOut = waitingFrameQueue.collectTimedOut()
                timedOut.forEach {
                    logger.warn("Frame timed out ${it.frame.toStringWithPredecessor()}, informing node scheduler ")
                    val noResultFrame = FrameNUL(network, it.frame)
                    it.sourceEndpoint.send(noResultFrame)
                }

                // Send this frame to ZWave (result is null when the select timed out)
                // Determine route
                if (result != null) {
                    when (result.endpoint) {
                        nodeZWSync.endpointB -> nodeZWEps = null
                        else -> routeFromNodeSchedulerToDriver(result.endpoint, result.frame, coroutineScope)
                    }
                }
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Send scheduler stopped" }
        }
    }

    /**
     * Function is scheduling the data frame (SOF) process
     */
    private suspend fun scheduleFromDriverToNodeScheduler(coroutineScope: CoroutineScope) {
        try {
            logger.info { "Receive scheduler started" }
            while (true) {
                // Select highest priority frame
                val result = framePrioritySelect(epZWData)

                // Route this frame to the right node scheduler
                routeFromDriverToNodeScheduler(result.endpoint, result.frame, coroutineScope)
            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Receive scheduler stopped" }
        }
    }


    private suspend fun softwareAndNodeScheduler(coroutineScope: CoroutineScope) {
        try {
            logger.info { "Software vs. node scheduler started" }
            var nodeSWEps: Collection<FrameDuplexPriorityChannelEndpoint>? = null
            while (true) {

                // Create list of endpoints from schedulers
                if (nodeSWEps == null)
                    nodeSWEps = nodeSchedulerExt.map { it.value.epSW }

                // Select highest priority frame
                val result = framePrioritySelect(epSW, nodeSWSync.endpointB, *nodeSWEps.toTypedArray())

                // Determine route
                when (result.endpoint) {
                    nodeSWSync.endpointB -> nodeSWEps = null
                    epSW -> routeFromSoftwareToNodeScheduler(result.endpoint, result.frame, coroutineScope)
                    else -> routeFromNodeSchedulerToSoftware(result.endpoint, result.frame, coroutineScope)
                }

            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "Software vs. node scheduler stopped" }
        }
    }
}