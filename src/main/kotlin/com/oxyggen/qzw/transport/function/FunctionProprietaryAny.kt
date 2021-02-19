package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionProprietaryAny {
    companion object : BinaryFunctionDeserializer {
        override fun getHandledSignatureBytes(): Set<Byte> {
            val result = mutableSetOf<Byte>()
            (FunctionID.PROPRIETARY_0.byteValue..FunctionID.PROPRIETARY_E.byteValue).forEach {
                result.add(it.toByte())
            }
            return result
        }

        override suspend fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request(context.functionID)
                FrameType.RESPONSE -> Response(context.functionID)
            }
    }

    open class Request(functionID: FunctionID) : FunctionRequest(functionID)
    open class Response(functionID: FunctionID) : FunctionResponse(functionID)

}            