package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.FrameID

open class BinaryFrameDeserializerContext(
    val frameId: FrameID,
) : BinaryDeserializerContext() {
    override fun getSignatureByte(): Byte = frameId.byteValue
}