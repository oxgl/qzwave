package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.frame.FrameState


interface FrameDuplexPriorityChannelEndpoint : DuplexPriorityChannelEndpoint<Frame> {

    suspend fun receiveFrameState(frameSOF: FrameSOF): FrameState

    override val remoteEndpoint: FrameDuplexPriorityChannelEndpoint

}