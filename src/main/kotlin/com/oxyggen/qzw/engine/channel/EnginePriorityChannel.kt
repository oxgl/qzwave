package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.engine.event.EngineEvent
import com.oxyggen.qzw.engine.event.EngineEventFrame
import com.oxyggen.qzw.engine.event.EngineEventFrameReceived
import com.oxyggen.qzw.engine.event.EngineEventFrameSend
import com.oxyggen.qzw.extensions.init
import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime

class EnginePriorityChannel {
    companion object {
        const val CHANNEL_PRIO_COUNT = 3
        const val CHANNEL_PRIO_STATE = 0             // ACK/NAK/CAN Frames
        const val CHANNEL_PRIO_NORMAL = 1           // Standard Frames
        const val CHANNEL_PRIO_LOW = 2              // Low priority frames
    }

    private val channels =
        sequence<Channel<EngineEvent>> { while (true) yield(Channel(Channel.UNLIMITED)) }.take(CHANNEL_PRIO_COUNT)
            .toList()

    suspend fun send(event: EngineEvent) {
        when (event) {
            is EngineEventFrame -> {
                if (event.frame is FrameState)
                    channels[CHANNEL_PRIO_STATE].send(event)
                else
                    channels[CHANNEL_PRIO_NORMAL].send(event)
            }
            else -> {
                channels[CHANNEL_PRIO_NORMAL].send(event)
            }
        }
    }

    fun init() {
        for (channel in channels) channel.init()
    }

    suspend fun sendFrame(frame: Frame) {
        send(EngineEventFrameSend(frame))
    }

    suspend fun receivedFrame(frame: Frame) {
        send(EngineEventFrameReceived(frame))
    }

    suspend fun receive(): EngineEvent = select {
        for (channel in channels) {
            channel.onReceive { it }
        }
    }

    suspend fun receiveFrameState(afterDateTime: LocalDateTime = LocalDateTime.now()): FrameState {
        var stateEvent: EngineEvent?
        while (true) {
            stateEvent = channels[CHANNEL_PRIO_STATE].receive()
            if (stateEvent is EngineEventFrame && stateEvent.created.isAfter(afterDateTime) && stateEvent.frame is FrameState) {
                return stateEvent.frame as FrameState
            }
        }
    }
}