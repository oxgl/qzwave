package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.frame.FrameState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.select

class FrameDuplexPriorityChannel(
    endpointAName: String = "endpointA",
    endpointBName: String = "endpointB"
) : DuplexPriorityChannel<Frame> {

    companion object {
        const val CHANNEL_PRIORITY_COUNT = 2
        const val CHANNEL_PRIORITY_STATE = 0            // ACK/NAK/CAN Frames
        const val CHANNEL_PRIORITY_NORMAL = 1           // Standard Frames
    }

    private class Endpoint(
        override val parent: FrameDuplexPriorityChannel,
        val name: String,
        val channelsIn: List<Channel<Frame>>,
        val channelsOut: List<Channel<Frame>>
    ) : FrameDuplexPriorityChannelEndpoint {
        override val priorities
            get() = 0 until CHANNEL_PRIORITY_COUNT

        override fun getReceiveChannel(priority: Int): ReceiveChannel<Frame> = channelsIn[priority]

        override fun offer(element: Frame) = when (element) {
            is FrameState ->
                channelsOut[CHANNEL_PRIORITY_STATE].offer(element)
            else ->
                channelsOut[CHANNEL_PRIORITY_NORMAL].offer(element)
        }


        override suspend fun send(element: Frame) {
            when (element) {
                is FrameState ->
                    channelsOut[CHANNEL_PRIORITY_STATE].send(element)
                else ->
                    channelsOut[CHANNEL_PRIORITY_NORMAL].send(element)
            }
        }

        override suspend fun receive(): Frame = select {
            for (channel in channelsIn) {
                channel.onReceive { it }
            }
        }

        override suspend fun receiveFrameState(frameSOF: FrameSOF): FrameState {
            while (true) {
                val frame = channelsIn[CHANNEL_PRIORITY_STATE].receive()
                if (frame.created.isAfter(frameSOF.created)) {
                    return frame.withPredecessor(frameSOF) as FrameState
                }
            }
        }

        override fun toString(): String = name
    }

    private val channelsAtoB =
        sequence<Channel<Frame>> { while (true) yield(Channel(Channel.UNLIMITED)) }.take(CHANNEL_PRIORITY_COUNT)
            .toList()
    private val channelsBtoA =
        sequence<Channel<Frame>> { while (true) yield(Channel(Channel.UNLIMITED)) }.take(CHANNEL_PRIORITY_COUNT)
            .toList()

    override val endpointA: FrameDuplexPriorityChannelEndpoint =
        Endpoint(this, endpointAName, channelsBtoA, channelsAtoB)
    override val endpointB: FrameDuplexPriorityChannelEndpoint =
        Endpoint(this, endpointBName, channelsAtoB, channelsBtoA)

    override fun toString(): String = "$endpointA <-> $endpointB"
}