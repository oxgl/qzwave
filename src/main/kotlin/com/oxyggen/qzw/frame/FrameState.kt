package com.oxyggen.qzw.frame


abstract class FrameState(predecessor: Frame? = null) : Frame(predecessor) {
    override val sendTimeouts: List<Long>
        get() = SENT_TIMEOUTS_SEND_ONLY

    //override fun isSuccessorOf(frame: Frame) = frame !is FrameState

}