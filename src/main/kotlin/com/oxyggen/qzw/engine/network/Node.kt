package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.NodeID

data class Node(val nodeID: NodeID, val supportedCommandClassVersion: Map<CommandClassID, Int> = mapOf()) {
    companion object {
        val SERIAL_API = Node(NodeID.SERIAL_API)
    }

    fun getSupportedCCVersion(commandClass: CommandClassID) = supportedCommandClassVersion[commandClass] ?: 1

    override fun toString(): String = if (nodeID == NodeID.SERIAL_API) "SERIAL_API" else "Node[$nodeID]"

}