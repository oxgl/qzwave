package com.oxyggen.qzw.engine.channel

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withTimeout
import kotlin.math.max
import kotlin.math.min

data class FramePrioritySelectResult(val endpoint: FrameDuplexPriorityChannelEndpoint, val frame: Frame)

suspend fun framePrioritySelect(vararg eps: FrameDuplexPriorityChannelEndpoint): FramePrioritySelectResult {
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
                        ep.getReceiveChannel(priority).onReceive { FramePrioritySelectResult(ep, it) }
    }
}

suspend fun framePrioritySelectWithTimeout(
    timeout: Long,
    vararg eps: FrameDuplexPriorityChannelEndpoint
): FramePrioritySelectResult? =
    if (timeout == 0L) {
        // No timeout parameter => standard select without timeout
        framePrioritySelect(*eps)
    } else {
        try {
            // Try to select with timeout
            withTimeout(timeout) {
                framePrioritySelect(*eps)
            }
        } catch (e: TimeoutCancellationException) {
            // Timeout, return null
            null
        }
    }

