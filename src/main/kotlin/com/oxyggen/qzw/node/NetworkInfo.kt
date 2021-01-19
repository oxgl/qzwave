package com.oxyggen.qzw.node

import com.oxyggen.qzw.types.NodeID

class NetworkInfo() : NetworkInfoGetter {
    var nodeMap: Map<NodeID, NodeInfo> = mutableMapOf()

    override val node: Map<NodeID, NodeInfo> get() = nodeMap
}