package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.engine.channel.FrameDuplexPriorityChannelEndpoint
import com.oxyggen.qzw.transport.frame.Frame
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.min

class WaitingFrameQueue(val timeout: Long = 65_000 /* 65 seconds */) {

    data class FrameInfo(
        val frame: Frame,
        val sourceEndpoint: FrameDuplexPriorityChannelEndpoint,
        val sent: LocalDateTime,
        val validity: LocalDateTime
    ) {
        fun isValid() = validity.isAfter(LocalDateTime.now())
    }

    private val frameInfos = mutableListOf<FrameInfo>()

    fun add(frame: Frame, sourceEndpoint: FrameDuplexPriorityChannelEndpoint): Boolean {
        val lastSOF = frame.lastSOF
        val sent = lastSOF?.sent
        return if (sent != null) {
            val validity = sent.plusNanos(timeout * 1_000_000)
            frameInfos += FrameInfo(frame, sourceEndpoint, sent, validity)
            frameInfos.sortBy { it.validity }
            true
        } else false
    }

    fun collectTimedOut(): Collection<FrameInfo> {
        val result = mutableListOf<FrameInfo>()
        while (frameInfos.size > 0 && !frameInfos[0].isValid())
            result.add(frameInfos.removeFirst())
        return result
    }

    /**
     * If predecessor of [frame] is waiting in queue then this
     * method returns pair of:
     * queuedPredecessor to [frame] (with filled predecessor)
     */
    fun dequeue(frame: Frame): Pair<FrameInfo?, Frame> {
        val frameSOF = frame.lastSOF
        return if (frameSOF != null) {
            // Find the waiting frame
            val frameInfo = frameInfos.find { it.isValid() && it.frame.isAwaitedResult(frameSOF) }
            if (frameInfo != null) {
                frameInfos.remove(frameInfo)
                frameInfo to frame.withPredecessor(frameInfo.frame)
            } else {
                null to frame
            }
        } else {
            null to frame
        }
    }

    fun nextTimeout(fromTime: LocalDateTime = LocalDateTime.now()): Long {
        val nextValidity = frameInfos.firstOrNull()?.validity
        return if (nextValidity == null || nextValidity.isBefore(fromTime)) {
            0
        } else {
            min(ChronoUnit.MILLIS.between(fromTime, nextValidity), 100)
        }
    }


    fun isEmpty() = frameInfos.isEmpty()

}