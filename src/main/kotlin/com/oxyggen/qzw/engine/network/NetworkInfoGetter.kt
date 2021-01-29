package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.NodeID

@OptIn(ExperimentalUnsignedTypes::class)
interface NetworkInfoGetter {
    val node: Map<NodeID, NodeInfo>

    fun getCurrentCallbackKey():FunctionCallbackKey
}