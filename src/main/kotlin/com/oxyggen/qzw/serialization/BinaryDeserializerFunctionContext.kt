package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.frame.FrameSOF

class BinaryDeserializerFunctionContext(
    signatureByte: Byte,
    parent: BinaryDeserializerFrameContext,
    val frameType: FrameSOF.FrameType
) :
    BinaryDeserializerContext(signatureByte, parent)
