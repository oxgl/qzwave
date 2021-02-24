package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameState
import java.time.LocalDateTime


interface FrameDuplexPriorityChannelEndpoint : DuplexPriorityChannelEndpoint<Frame> {
    suspend fun receiveFrameState(afterDateTime: LocalDateTime): FrameState
}