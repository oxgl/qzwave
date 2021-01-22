package com.oxyggen.qzw.engine

import java.time.LocalDateTime

data class EngineEvent(val type: Type, val data: Any? = null, val created: LocalDateTime = LocalDateTime.now()) {
    enum class Type {
        ABORT,
        FRAME_SEND,
        FRAME_RECEIVED
    }

    companion object {
        val EVENT_ABORT = EngineEvent(Type.ABORT)
    }
}