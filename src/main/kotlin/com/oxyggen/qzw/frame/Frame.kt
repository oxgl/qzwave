package com.oxyggen.qzw.frame

import com.oxyggen.qzw.node.NetworkInfoGetter
import com.oxyggen.qzw.serialization.SerializableFrameContext
import java.io.OutputStream

abstract class Frame {
    abstract fun serialize(outputStream: OutputStream, context: SerializableFrameContext)
}