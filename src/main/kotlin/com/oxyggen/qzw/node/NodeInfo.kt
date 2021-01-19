package com.oxyggen.qzw.node

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.NodeID

data class NodeInfo(val nodeID: NodeID, val supportedCommandClassVersion: Map<CommandClassID, Int>) {
    fun getSupportedCCVersion(commandClass: CommandClassID) = supportedCommandClassVersion[commandClass] ?: 1

    companion object {
        fun getInitial(nodeID: NodeID) = NodeInfo(nodeID, mapOf())
    }

}