package com.oxyggen.qzw.engine.event

import com.oxyggen.qzw.transport.frame.Frame
import java.time.LocalDateTime

abstract class EngineEventFrame(val frame: Frame, created: LocalDateTime = LocalDateTime.now()) : EngineEvent(created) {
    override fun toString(): String = "EVENT_FRAME: $frame"
}