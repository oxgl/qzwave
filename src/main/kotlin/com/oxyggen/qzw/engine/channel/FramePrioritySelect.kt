package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.selects.select

suspend fun framePrioritySelect(vararg priorityChannels: FramePriorityReceiveChannel) =
    select<Pair<FramePriorityReceiveChannel, Frame>> {
        for (priority in 0 until FramePriorityChannel.CHANNEL_PRIO_COUNT) {
            for (priorityChannel in priorityChannels) {
                priorityChannel[priority].onReceive { priorityChannel to it }
            }
        }
    }