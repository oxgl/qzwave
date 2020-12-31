package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

class BinaryFunctionDeserializerContext(
    frameId: FrameID,
    val frameType: FrameType,
    val functionId: FunctionID,
) : BinaryFrameDeserializerContext(frameId) {
    override fun getSignatureByte(): Byte = functionId.byteValue
}
