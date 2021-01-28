package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionTemplate {

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.DEBUG_OUTPUT.byteValue)

        override fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request()
                FrameType.RESPONSE -> Response()
            }
    }

    class Request : FunctionRequest(FunctionID.DEBUG_OUTPUT)

    class Response : FunctionResponse(FunctionID.DEBUG_OUTPUT)

}