package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

class BinaryCommandDeserializerContext(
    frameId: FrameID,
    val frameType: FrameType,
    val functionId: FunctionID,
    val commandClassID: CommandClassID,
    val version: Int = 1
) : BinaryFrameDeserializerContext(frameId) {
    override fun getSignatureByte(): Byte = commandClassID.byteValue
}
