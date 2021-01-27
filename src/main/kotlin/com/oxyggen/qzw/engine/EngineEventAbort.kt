package com.oxyggen.qzw.engine

import java.time.LocalDateTime

class EngineEventAbort(created: LocalDateTime = LocalDateTime.now()) : EngineEvent(created) {
    override fun toString(): String = "ABORT"
}