package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.FrameID

open class BinaryFrameDeserializerContext(
    val frameID: FrameID,
) : BinaryDeserializerContext() {
    override fun getSignatureByte(): Byte = frameID.byteValue
}