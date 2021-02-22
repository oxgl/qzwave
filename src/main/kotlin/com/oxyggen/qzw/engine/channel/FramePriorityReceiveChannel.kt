package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameState
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.SelectClause1
import java.time.LocalDateTime

interface FramePriorityReceiveChannel {

    operator fun get(priority:Int):ReceiveChannel<Frame>

    suspend fun receiveFrameState(afterDateTime: LocalDateTime = LocalDateTime.now()): FrameState

}