package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.FunctionCallbackKey
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import java.io.OutputStream
import java.time.LocalDateTime

abstract class Frame(predecessor: Frame? = null) {

    val created: LocalDateTime = LocalDateTime.now()

    var predecessor: Frame? = predecessor
        protected set

    companion object {
        val SEND_TIMEOUTS_DEFAULT = generateSequence(200L) { it + 1000 }.take(4).toList()
        val SENT_TIMEOUTS_SEND_ONLY = listOf<Long>(0)
    }

    abstract val sendTimeouts: List<Long>

    abstract fun isFunctionCallbackKeyRequired(): Boolean

    abstract fun getFunctionCallbackKey(): FunctionCallbackKey?

    abstract fun serialize(outputStream: OutputStream, context: SerializableFrameContext)

    //abstract fun isSuccessorOf(frame: Frame): Boolean

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
        return result + toString()
    }

}