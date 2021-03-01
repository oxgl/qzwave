package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.function.FunctionRequest
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import com.oxyggen.qzw.types.NodeID
import java.io.OutputStream
import java.time.LocalDateTime

abstract class Frame(val network: Network, predecessor: Frame? = null) {

    val created: LocalDateTime = LocalDateTime.now()

    var predecessor: Frame? = predecessor
        protected set

    abstract suspend fun serialize(outputStream: OutputStream, context: SerializableFrameContext)

    abstract fun getNode(): Node?

    abstract fun isAwaitingResult(): Boolean

    abstract fun isAwaitedResult(frameSOF: FrameSOF): Boolean

    internal open fun withPredecessor(predecessor: Frame): Frame? {
        this.predecessor = predecessor
        return this
    }

    open fun toStringWithPredecessor(): String =
        predecessor?.let { predecessor?.toStringWithPredecessor() + " -> " } + toString()

}