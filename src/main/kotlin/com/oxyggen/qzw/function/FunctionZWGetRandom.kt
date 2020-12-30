package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionId
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionZWGetRandom : Function() {
    companion object : BinaryFunctionDeserializer {
        private val SIGNATURE = FunctionId.ZW_GET_RANDOM.byteValue

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

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

    class Request(val noRandomBytes: Byte = 0x00) : FunctionRequest() {
        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            outputStream.putByte(SIGNATURE)
            outputStream.putByte(noRandomBytes)
        }

        override fun toString(): String = "ZW_GET_RANDOM($noRandomBytes)"
    }

    class Response(val success: Boolean, val randomBytes: ByteArray) : FunctionResponse() {
        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            outputStream.putByte(SIGNATURE)
            outputStream.putByte(randomBytes.size.toByte())
            outputStream.write(randomBytes)
        }

        override fun toString(): String = "ZW_GET_RANDOM(${randomBytes.size}, ${randomBytes.toList()})"
    }


}            