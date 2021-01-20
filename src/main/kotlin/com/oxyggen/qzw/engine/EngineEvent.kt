package com.oxyggen.qzw.engine

data class EngineEvent(val type: Type, val data: Any? = null) {
    enum class Type {
        ABORT,
        FRAME_SEND,
        FRAME_RECEIVED
    }

    companion object {
        val EVENT_ABORT = EngineEvent(Type.ABORT)
    }
}