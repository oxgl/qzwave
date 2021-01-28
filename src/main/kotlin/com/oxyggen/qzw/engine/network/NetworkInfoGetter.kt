package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.NodeID

interface NetworkInfoGetter {
    val node: Map<NodeID, NodeInfo>
}