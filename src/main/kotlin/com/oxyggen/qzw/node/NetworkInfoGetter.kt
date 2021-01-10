package com.oxyggen.qzw.node

import com.oxyggen.qzw.types.NodeID

interface NetworkInfoGetter {
    val node: Map<NodeID, NodeInfo>
}