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
        const val CHANNEL_PRIORITY_COUNT = 3
        const val CHANNEL_PRIORITY_STATE = 0                // ACK/NAK/CAN Frames
        const val CHANNEL_PRIORITY_HIGH = 1                 // High priority frames (for battery devices)
        const val CHANNEL_PRIORITY_NORMAL = 2               // Standard Frames
        const val CHANNEL_PRIORITY_DEFAULT = -1

        val CHANNEL_PRIORITY_ALL = (0 until CHANNEL_PRIORITY_COUNT).toList()
    }

    private class Endpoint(
        override val channel: FrameDuplexPriorityChannel,
        val parentEndpoint: Endpoint? = null,
        override val priorities: Collection<Int>,
        override val name: String,
        val channelsIn: Map<Int, Channel<Frame>>,
        val channelsOut: Map<Int, Channel<Frame>>
    ) : FrameDuplexPriorityChannelEndpoint {

        override val remoteEndpoint: FrameDuplexPriorityChannelEndpoint
            get() = if (channel.endpointA == this) channel.endpointB else channel.endpointA

        override fun getReceiveChannel(priority: Int): ReceiveChannel<Frame> = channelsIn[priority]
            ?: throw Exception("Internal error! Endpoint does not contain channel with priority $priority!")

        override fun getPartialChannelEndpoint(
            priorityFilter: (Int) -> Boolean,
            subChannelName: String?
        ): FrameDuplexPriorityChannelEndpoint {
            val filteredPriorities = priorities.filter { priorityFilter(it) }
            return Endpoint(
                channel,
                this,
                filteredPriorities,
                subChannelName ?: name,
                channelsIn,
                channelsOut
            )
        }

        override fun splitChannelEndpoint(
            belongsToFirst: (Int) -> Boolean,
            firstEndpointName: String?,
            secondEndpointName: String?
        ): Pair<FrameDuplexPriorityChannelEndpoint, FrameDuplexPriorityChannelEndpoint> =
            Endpoint(
                channel,
                this,
                priorities.filter { belongsToFirst(it) },
                firstEndpointName ?: "$name/1",
                channelsIn,
                channelsOut
            ) to Endpoint(
                channel,
                this,
                priorities.filter { !belongsToFirst(it) },
                secondEndpointName ?: "$name/2",
                channelsIn,
                channelsOut
            )

        private fun determinePriority(element: Frame, priority: Int?): Int {
            val determinedPriority =
                if (priority != null && priority in priorities && priority != CHANNEL_PRIORITY_STATE) priority
                else when (element) {
                    is FrameState -> CHANNEL_PRIORITY_STATE
                    else -> CHANNEL_PRIORITY_NORMAL
                }
            if (determinedPriority !in channelsOut.keys)
                throw Exception("Internal error! Endpoint does not contain channel with priority $determinedPriority!")
            return determinedPriority
        }

        override fun offer(element: Frame, priority: Int?) =
            channelsOut[determinePriority(element, priority)]?.offer(element) ?: false

        override suspend fun send(element: Frame, priority: Int?) {
            channelsOut[determinePriority(element, priority)]?.send(element)
        }

        override suspend fun receive(): Frame = select {
            for (channel in channelsIn) {
                channel.value.onReceive { it }
            }
        }

        override suspend fun receiveFrameState(frameSOF: FrameSOF): FrameState {
            val channelIn = channelsIn[CHANNEL_PRIORITY_STATE]
                ?: throw Exception("Internal error! Endpoint does not contain channel with priority $CHANNEL_PRIORITY_STATE!")
            while (true) {
                val frame = channelIn.receive()
                if (frame.created.isAfter(frameSOF.created)) {
                    return frame.withPredecessor(frameSOF) as FrameState
                }
            }
        }

        override fun toString(): String = name
    }

    private fun createChannelList(): Map<Int, Channel<Frame>> {
        val result = mutableMapOf<Int, Channel<Frame>>()
        for (priority in 0 until CHANNEL_PRIORITY_COUNT) result[priority] = Channel(Channel.UNLIMITED)
        return result
    }

    private val channelsAtoB = createChannelList()
    private val channelsBtoA = createChannelList()

    override val endpointA: FrameDuplexPriorityChannelEndpoint =
        Endpoint(this, null, CHANNEL_PRIORITY_ALL, endpointAName, channelsBtoA, channelsAtoB)
    override val endpointB: FrameDuplexPriorityChannelEndpoint =
        Endpoint(this, null, CHANNEL_PRIORITY_ALL, endpointBName, channelsAtoB, channelsBtoA)

    override fun toString(): String = "$endpointA <-> $endpointB"
}