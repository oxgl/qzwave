package com.oxyggen.qzw.frame

import com.oxyggen.qzw.node.NetworkInfoGetter
import com.oxyggen.qzw.serialization.SerializableFrameContext
import java.io.OutputStream

abstract class Frame {
    companion object {
        val SEND_TIMEOUTS_DEFAULT = listOf<Long>(100, 1100, 2100, 3100)
        val SENT_TIMEOUTS_SEND_ONLY = listOf<Long>(0)

    }

    abstract fun serialize(outputStream: OutputStream, context: SerializableFrameContext)

    abstract val sendTimeouts: List<Long>
}