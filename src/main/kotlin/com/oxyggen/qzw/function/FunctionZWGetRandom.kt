package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionZWGetRandom {
    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_RANDOM.byteValue)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
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

            fun deserialize(inputStream: InputStream): Request =
                mapper.deserialize<Request>(inputStream.readAllBytes())
        }

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.write(mapper.serialize(this))
        }

        override fun toString(): String = "$functionId($noRandomBytes)"
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

            fun deserialize(inputStream: InputStream): Response =
                mapper.deserialize<Response>(inputStream.readAllBytes())
        }

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.write(mapper.serialize(this))
        }

        override fun toString(): String = "$functionId(out ${randomBytes.size}, out ${randomBytes.toList()})"
    }

}            