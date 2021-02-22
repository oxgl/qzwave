package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.engine.channel.FramePriorityChannel
import com.oxyggen.qzw.engine.channel.FramePriorityChannel.Direction
import com.oxyggen.qzw.engine.channel.FramePriorityReceiveChannel
import com.oxyggen.qzw.engine.channel.FramePrioritySendChannel

class NodeScheduler(
    val parent: NetworkScheduler,
    val node: Node,
    val fromSW: FramePriorityReceiveChannel = FramePriorityChannel(Direction.FROM_SW),          // From software component (in)
    val toSW: FramePrioritySendChannel = FramePriorityChannel(Direction.TO_SW),                 // To software component (out)
    val fromZW: FramePriorityReceiveChannel = FramePriorityChannel(Direction.FROM_ZW),          // From ZWave driver (received)
    val toZW: FramePrioritySendChannel = FramePriorityChannel(Direction.TO_ZW)                  // To ZWave driver (send)
)