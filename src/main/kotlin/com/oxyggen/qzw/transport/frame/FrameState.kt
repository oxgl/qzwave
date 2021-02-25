package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.types.NodeID


abstract class FrameState(network: Network, predecessor: Frame? = null) : Frame(network, predecessor) {
    override fun withPredecessor(predecessor: Frame): FrameState {
        return super.withPredecessor(predecessor) as FrameState
    }
}