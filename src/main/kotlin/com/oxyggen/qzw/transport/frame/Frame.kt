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

    var sent: LocalDateTime? = null
        protected set

    var predecessor: Frame? = predecessor
        protected set

    val lastPredecessorSOF: FrameSOF? by lazy<FrameSOF> { findPredecessor { it is FrameSOF } as FrameSOF }

    internal fun setSent(time: LocalDateTime = LocalDateTime.now()) {
        sent = time
    }

    fun findPredecessor(filter: (frame: Frame) -> Boolean): Frame? {
        var frame: Frame? = this
        while (frame != null && !filter(frame)) {
            frame = frame.predecessor
        }
        return frame
    }

    abstract suspend fun serialize(outputStream: OutputStream, context: SerializableFrameContext)

    abstract fun getNode(): Node?

    abstract fun isAwaitingResult(): Boolean

    abstract fun isAwaitedResult(frameSOF: FrameSOF): Boolean

    internal open fun withPredecessor(predecessor: Frame): Frame {
        val firstFrame = findPredecessor { it.predecessor == null }
        if (firstFrame != null) firstFrame.predecessor = predecessor
        return this
    }

    open fun toStringWithPredecessor(): String {
        var result = ""
        predecessor?.let { result += predecessor?.toStringWithPredecessor() + " -> " }
        result += toString()
        return result
    }


}