package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.transport.frame.Frame
import java.time.LocalDateTime

class WaitingFrameQueue(val timeout: Long = 65_000 /* 65 seconds */) {

    private data class FrameInfo(val frame: Frame, val sent: LocalDateTime, val validity: LocalDateTime) {
        fun isValid() = validity.isAfter(LocalDateTime.now())
    }

    private val frameInfos = mutableListOf<FrameInfo>()

    fun add(frame: Frame): Boolean {
        val lastSOF = frame.lastPredecessorSOF
        val sent = lastSOF?.sent
        return if (sent != null) {
            val validity = sent.plusNanos(timeout * 1_000_000)
            frameInfos += FrameInfo(frame, sent, validity)
            frameInfos.sortBy { it.validity }
            true
        } else false
    }

    fun collectTimedOut(): Collection<Frame> {
        val result = mutableListOf<Frame>()
        while (frameInfos.size > 0 && !frameInfos[0].isValid())
            result.add(frameInfos.removeFirst().frame)
        return result
    }

    /**
     * If predecessor for [frame] is waiting in queue then this
     * method returns pair of:
     * queuedPredecessor to [frame] (with filled predecessor)
     */
    fun dequeue(frame: Frame): Pair<Frame?, Frame> {
        val frameSOF = frame.lastPredecessorSOF
        return if (frameSOF != null) {
            // Find the waiting frame
            val frameInfo = frameInfos.find { it.isValid() && it.frame.isAwaitedResult(frameSOF) }
            if (frameInfo != null) {
                frameInfos.remove(frameInfo)
                frameInfo.frame to frame.withPredecessor(frameInfo.frame)
            } else {
                null to frame
            }
        } else {
            null to frame
        }
    }

    fun isEmpty() = frameInfos.isEmpty()

}