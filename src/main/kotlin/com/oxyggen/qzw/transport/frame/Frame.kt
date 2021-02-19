package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import com.oxyggen.qzw.types.NodeID
import java.io.OutputStream
import java.time.LocalDateTime

abstract class Frame(val network: Network, predecessor: Frame? = null) {

    val created: LocalDateTime = LocalDateTime.now()

    var predecessor: Frame? = predecessor
        protected set

    companion object {
        val SEND_TIMEOUTS_DEFAULT = generateSequence(200L) { it + 1000 }.take(4).toList()
        val SENT_TIMEOUTS_SEND_ONLY = listOf<Long>(0)
    }

    abstract val sendTimeouts: List<Long>

    //abstract fun isFunctionCallbackKeyRequired(): Boolean

    //abstract fun getFunctionCallbackKey(): FunctionCallbackKey?

    abstract suspend fun serialize(outputStream: OutputStream, context: SerializableFrameContext)

    abstract fun getNodeId(): NodeID?

    open fun withPredecessor(predecessor: Frame): Frame {
        this.predecessor = predecessor
        return this
    }

    open fun toStringWithPredecessor(): String =
        predecessor?.let { predecessor?.toStringWithPredecessor() + " -> " } + toString()

}