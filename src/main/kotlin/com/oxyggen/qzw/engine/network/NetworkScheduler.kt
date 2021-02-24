package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannel.Connection
import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannelEndpoint
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

    private var loopJob: Job? = null
    private val loopJobMutex = Mutex()

    private var nodeSchedulerExt = mutableMapOf<Node, NodeSchedulerExt>()

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

    private suspend fun loop(coroutineScope: CoroutineScope) {
        try {
            logger.debug { "Network scheduler: started" }
            while (true) {


            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "Network scheduler: stopped" }
        }
    }
}