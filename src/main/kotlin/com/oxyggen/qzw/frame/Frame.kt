package com.oxyggen.qzw.frame

import com.oxyggen.qzw.serialization.SerializableFrameContext
import java.io.OutputStream
import java.time.LocalDateTime

abstract class Frame(predecessor: Frame? = null) {

    val created: LocalDateTime = LocalDateTime.now()

    var predecessor: Frame? = predecessor
        protected set

    companion object {
        val SEND_TIMEOUTS_DEFAULT = listOf<Long>(100, 1100, 2100, 3100)
        val SENT_TIMEOUTS_SEND_ONLY = listOf<Long>(0)
    }

    abstract val sendTimeouts: List<Long>

    abstract fun serialize(outputStream: OutputStream, context: SerializableFrameContext)

    open fun withPredecessor(predecessor: Frame): Frame {
        this.predecessor = predecessor
        return this
    }

    open fun toStringWithPredecessor(): String {
        var result = ""
        if (predecessor != null) {
            result += predecessor?.toStringWithPredecessor()
            result += " -> "
        }
        return result
    }

}