package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.function.FunctionRequest
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import com.oxyggen.qzw.types.NodeID
import org.apache.logging.log4j.kotlin.logger
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.logging.Level
import java.util.logging.Logger

abstract class Frame(val network: Network, predecessor: Frame? = null) {

    val created: LocalDateTime = LocalDateTime.now()

    var sent: LocalDateTime? = null
        protected set

    var predecessor: Frame? = predecessor
        protected set

    val lastSOF: FrameSOF? by lazy { findPredecessor(startFrom = this) { it is FrameSOF } as FrameSOF }

    val predecessorSOF: FrameSOF? by lazy { findPredecessor(startFrom = predecessor) { it is FrameSOF } as FrameSOF }

    val responseTime: Long
        get() {
            val predecessorSent: LocalDateTime? = predecessor?.sent ?: predecessor?.created
            return if (predecessorSent != null) {
                ChronoUnit.MILLIS.between(predecessorSent, sent ?: created)
            } else {
                -1L
            }
        }

    internal fun setSent(time: LocalDateTime = LocalDateTime.now()) {
        sent = time
    }


    private fun findPredecessor(startFrom: Frame? = this, filter: (frame: Frame) -> Boolean): Frame? {
        var frame: Frame? = startFrom
        while (frame != null && !filter(frame)) {
            frame = frame.predecessor
        }
        return frame
    }

    fun isPredecessorOf(successor: Frame): Boolean {
        var frame: Frame? = successor
        while (frame != null) {
            if (frame == this) return true
            frame = frame.predecessor
        }
        return false
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