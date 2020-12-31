package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
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
            FrameType.REQUEST -> {
                val noRandomBytes = inputStream.getByte()
                Request(noRandomBytes)
            }
            FrameType.RESPONSE -> {
                val successByte = inputStream.getByte()
                val noRandomBytes = inputStream.getUByte()
                val randomBytes = inputStream.readNBytes(noRandomBytes.toInt())

                Response(successByte > 0, randomBytes)
            }
        }


    }

    class Request(val noRandomBytes: Byte = 0x00) : FunctionRequest(FunctionID.ZW_GET_RANDOM) {
        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.putByte(noRandomBytes)
        }

        override fun toString(): String = "$functionId($noRandomBytes)"
    }

    class Response(val success: Boolean, val randomBytes: ByteArray) : FunctionResponse(FunctionID.ZW_GET_RANDOM) {
        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.putByte(randomBytes.size.toByte())
            outputStream.write(randomBytes)
        }

        override fun toString(): String = "$functionId(out ${randomBytes.size}, out ${randomBytes.toList()})"
    }


}            