package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionId
import java.io.InputStream

abstract class FunctionProprietaryAny : Function() {
    companion object : BinaryFunctionDeserializer {
        override fun getHandledSignatureBytes(): Set<Byte> {
            val result = mutableSetOf<Byte>()
            (FunctionId.PROPRIETARY_0.byteValue..FunctionId.PROPRIETARY_E.byteValue).forEach {
                result.add(it.toByte())
            }
            return result
        }

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

    open class Request:FunctionRequest()
    open class Response:FunctionResponse()

}            