package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.selects.select

class NetworkScheduler : FrameScheduler() {

    private var nodeScheduler = mutableMapOf<NodeID, NodeScheduler>()

    fun getLocalNode() = NodeID(1)

    operator fun get(nodeID: NodeID): NodeScheduler = nodeScheduler.getOrPut(nodeID, { NodeScheduler(this, nodeID) })

    private suspend fun loop() {
        select<Frame> { }
    }

    fun send(frame: Frame) {


    }


}