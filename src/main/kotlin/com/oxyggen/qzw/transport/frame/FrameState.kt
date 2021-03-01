package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node

abstract class FrameState(network: Network, predecessor: Frame? = null) : Frame(network, predecessor) {
    override fun withPredecessor(predecessor: Frame): FrameState = super.withPredecessor(predecessor) as FrameState

    override fun getNode(): Node? = predecessor?.getNode()

    override fun isAwaitingResult(): Boolean = predecessor?.isAwaitingResult() ?: false

    override fun isAwaitedResult(frameSOF: FrameSOF): Boolean = predecessor?.isAwaitedResult(frameSOF) ?: false

}