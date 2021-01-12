package com.oxyggen.qzw.engine

data class EngineEvent(val type: Type) {
    enum class Type {
        ABORT
    }

    companion object {
        val EVENT_ABORT = EngineEvent(Type.ABORT)
    }
}