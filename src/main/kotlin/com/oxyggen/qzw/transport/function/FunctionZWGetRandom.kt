package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.extensions.getAllBytes
import com.oxyggen.qzw.extensions.putBytes
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionZWGetRandom {
    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_RANDOM.byteValue)

        override suspend fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function = when (context.frameType) {
            FrameType.REQUEST -> Request.deserialize(inputStream)
            FrameType.RESPONSE -> Response.deserialize(inputStream)
        }


    }

    class Request(val noRandomBytes: Byte = 0x00) : FunctionRequest(FunctionID.ZW_GET_RANDOM) {
        companion object {
            private val mapper by lazy {
                mapper<Request> {
                    byte("noRandomBytes")
                }
            }

            suspend fun deserialize(inputStream: InputStream): Request =
                mapper.deserialize(inputStream.getAllBytes())
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this))
        }

        override fun toString(): String = "$functionID($noRandomBytes)"
    }

    class Response(val success: Boolean, val randomBytes: ByteArray) : FunctionResponse(FunctionID.ZW_GET_RANDOM) {
        companion object {
            private val mapper by lazy {
                mapper<Request> {
                    byte("success")
                    byte("#randomBytesSize")
                    byteCol("randomBytes", length = "@randomBytesSize")
                }
            }

            suspend fun deserialize(inputStream: InputStream): Response =
                mapper.deserialize(inputStream.getAllBytes())
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this))
        }

        override fun toString(): String = "$functionID(out ${randomBytes.size}, out ${randomBytes.toList()})"
    }

}            