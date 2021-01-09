package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

open class BinaryFunctionDeserializerContext(
    frameID: FrameID,
    val frameType: FrameType,
    val functionID: FunctionID,
) : BinaryFrameDeserializerContext(frameID) {
    override fun getSignatureByte(): Byte = functionID.byteValue
}
