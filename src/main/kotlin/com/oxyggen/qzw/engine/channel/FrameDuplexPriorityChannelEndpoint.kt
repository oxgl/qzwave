package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.frame.FrameState


interface FrameDuplexPriorityChannelEndpoint : DuplexPriorityChannelEndpoint<Frame> {

    val name: String

    suspend fun receiveFrameState(frameSOF: FrameSOF): FrameState

    override val remoteEndpoint: FrameDuplexPriorityChannelEndpoint

    override fun getPartialChannelEndpoint(
        priorityFilter: (Int) -> Boolean,
        subChannelName: String?
    ): FrameDuplexPriorityChannelEndpoint

    override fun splitChannelEndpoint(
        belongsToFirst: (Int) -> Boolean,
        firstEndpointName: String?,
        secondEndpointName: String?
    ): Pair<FrameDuplexPriorityChannelEndpoint, FrameDuplexPriorityChannelEndpoint>

}