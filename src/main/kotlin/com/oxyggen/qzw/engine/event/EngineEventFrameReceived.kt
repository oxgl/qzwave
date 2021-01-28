package com.oxyggen.qzw.engine.event

import com.oxyggen.qzw.transport.frame.Frame
import java.time.LocalDateTime

open class EngineEventFrameReceived(frame: Frame, created: LocalDateTime = LocalDateTime.now()) :
    EngineEventFrame(frame, created) {
    override fun toString(): String = "FRAME_RECEIVED: $frame"
}