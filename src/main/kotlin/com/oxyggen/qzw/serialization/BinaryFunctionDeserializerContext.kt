package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.types.FrameType

class BinaryFunctionDeserializerContext(
    signatureByte: Byte,
    parent: BinaryFrameDeserializerContext,
    val frameType: FrameType
) :
    BinaryDeserializerContext(signatureByte, parent)
