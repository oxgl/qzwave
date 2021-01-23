package com.oxyggen.qzw.frame


abstract class FrameState : Frame() {
    override val sendTimeouts: List<Long>
        get() = SENT_TIMEOUTS_SEND_ONLY
}