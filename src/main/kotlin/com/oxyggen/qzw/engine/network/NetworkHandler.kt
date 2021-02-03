package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.NodeID

class NetworkHandler {
    private var nodeMap = mutableMapOf<NodeID, NodeHandler>()

    operator fun get(nodeID: NodeID): NodeHandler = nodeMap.getOrPut(nodeID, { NodeHandler(nodeID) })



}