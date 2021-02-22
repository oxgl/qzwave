package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.engine.channel.FramePriorityChannel
import com.oxyggen.qzw.engine.channel.FramePriorityChannel.Direction
import com.oxyggen.qzw.engine.channel.FramePriorityReceiveChannel
import com.oxyggen.qzw.engine.channel.FramePrioritySendChannel
import com.oxyggen.qzw.engine.channel.framePrioritySelect
import com.oxyggen.qzw.types.NodeID
import org.apache.logging.log4j.kotlin.Logging


class NetworkScheduler(
    val network: Network = Network(),
    val fromSW: FramePriorityReceiveChannel = FramePriorityChannel(Direction.FROM_SW),          // From software component (in)
    val toSW: FramePrioritySendChannel = FramePriorityChannel(Direction.TO_SW),                 // To software component (out)
    val fromZW: FramePriorityReceiveChannel = FramePriorityChannel(Direction.FROM_ZW),          // From ZWave driver (received)
    val toZW: FramePrioritySendChannel = FramePriorityChannel(Direction.TO_ZW)                  // To ZWave driver (send)
) : Logging {

    private var nodeScheduler = mutableMapOf<Node, NodeScheduler>()

    fun getLocalNodeId() = NodeID(1)

    operator fun get(node: Node): NodeScheduler = nodeScheduler.getOrPut(node, { NodeScheduler(this, node) })

    private suspend fun loop() {
        logger.debug { "Network scheduler: started" }
        var isActive = true
        while (isActive) {
            val received = framePrioritySelect(fromSW, fromZW)

            //when (received.first)


        }
        logger.debug { "Network scheduler: stopped" }
    }

}