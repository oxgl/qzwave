package com.oxyggen.qzw.engine

data class EngineEvent(val type: Type, val data: Any? = null) {
    enum class Type {
        ABORT,
        SEND_FRAME
    }

    companion object {
        val EVENT_ABORT = EngineEvent(Type.ABORT)
    }
}