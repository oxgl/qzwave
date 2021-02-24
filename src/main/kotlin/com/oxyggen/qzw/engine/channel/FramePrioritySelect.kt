package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.selects.select
import kotlin.math.max
import kotlin.math.min

suspend fun framePrioritySelect(vararg eps: FrameDuplexPriorityChannelEndpoint): Pair<FrameDuplexPriorityChannelEndpoint, Frame> {
    // Find the min/max channel priority from endpoints
    var minPriority = Int.MAX_VALUE
    var maxPriority = Int.MIN_VALUE
    for (ep in eps) {
        minPriority = min(minPriority, ep.priorities.first)
        maxPriority = max(maxPriority, ep.priorities.last)
    }
    // Select frame with highest priority
    return select {
        if (minPriority <= maxPriority)
            for (priority in minPriority..maxPriority)
                for (ep in eps)
                    if (priority in ep.priorities)
                        ep.getReceiveChannel(priority).onReceive { ep to it }
    }
}
