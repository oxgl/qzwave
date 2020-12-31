package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import java.io.InputStream

abstract class FunctionTemplate {

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.DEBUG_OUTPUT.byteValue)

        // HOST->ZW: REQ | 0x15
        // ZW->HOST: RES | 0x15 | buffer (12 bytes) | library type
        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request()
                FrameType.RESPONSE -> Response()
            }
    }

    class Request : FunctionRequest(FunctionID.DEBUG_OUTPUT)

    class Response : FunctionResponse(FunctionID.DEBUG_OUTPUT)

}