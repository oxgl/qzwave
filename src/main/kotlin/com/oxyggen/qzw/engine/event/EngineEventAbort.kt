package com.oxyggen.qzw.engine.event

import java.time.LocalDateTime

class EngineEventAbort(created: LocalDateTime = LocalDateTime.now()) : EngineEvent(created) {
    override fun toString(): String = "ABORT"
}