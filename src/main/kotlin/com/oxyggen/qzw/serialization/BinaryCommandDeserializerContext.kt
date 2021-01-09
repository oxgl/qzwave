package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

class BinaryCommandDeserializerContext(
    frameID: FrameID,
    frameType: FrameType,
    functionID: FunctionID,
    val commandClassID: CommandClassID,
    val version: Int = 1
) : BinaryFunctionDeserializerContext(frameID, frameType, functionID) {
    override fun getSignatureByte(): Byte = commandClassID.byteValue
}
