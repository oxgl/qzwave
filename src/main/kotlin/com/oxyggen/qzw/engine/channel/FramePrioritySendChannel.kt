package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.channels.SendChannel

interface FramePrioritySendChannel {

    operator fun get(priority:Int): SendChannel<Frame>

    suspend fun send(frame: Frame)
}