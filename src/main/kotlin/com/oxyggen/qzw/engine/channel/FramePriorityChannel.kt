package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.extensions.init
import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameState
import kotlinx.coroutines.channels.Channel
import java.time.LocalDateTime

class FramePriorityChannel(val direction: Direction) : FramePrioritySendChannel,
    FramePriorityReceiveChannel {
    enum class Direction {
        TO_ZW,
        FROM_ZW,
        TO_SW,
        FROM_SW
    }

    companion object {
        const val CHANNEL_PRIO_COUNT = 2
        const val CHANNEL_PRIO_STATE = 0            // ACK/NAK/CAN Frames
        const val CHANNEL_PRIO_NORMAL = 1           // Standard Frames
    }

    private val channels =
        sequence<Channel<Frame>> { while (true) yield(Channel(Channel.UNLIMITED)) }.take(CHANNEL_PRIO_COUNT)
            .toList()

    /* FramePrioritySendChannel */
    override suspend fun send(frame: Frame) {
        when (frame) {
            is FrameState ->
                channels[CHANNEL_PRIO_STATE].send(frame)
            else ->
                channels[CHANNEL_PRIO_NORMAL].send(frame)
        }
    }

    fun init() {
        for (channel in channels) channel.init()
    }

    override operator fun get(priority: Int) =
        if (priority in channels.indices) channels[priority] else channels[CHANNEL_PRIO_NORMAL]

    /* FramePriorityReceiveChannel */
    override suspend fun receiveFrameState(afterDateTime: LocalDateTime): FrameState {
        while (true) {
            val frame = channels[CHANNEL_PRIO_STATE].receive()
            if (frame.created.isAfter(afterDateTime)) {
                return frame as FrameState
            }
        }
    }

}