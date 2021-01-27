package com.oxyggen.qzw.engine

import com.oxyggen.qzw.frame.Frame
import java.time.LocalDateTime

open class EngineEventFrameSend(frame: Frame, created: LocalDateTime = LocalDateTime.now()) :
    EngineEventFrame(frame, created) {
    override fun toString(): String = "FRAME_SEND: $frame"
}