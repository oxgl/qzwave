@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.frame

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.factory.FunctionFactory
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFrameContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.experimental.xor

open class FrameSOF(val frameType: FrameType, val function: Function) : Frame() {

    enum class FrameType(val byteValue: Byte) {
        REQUEST(0x00),
        RESPONSE(0x01);

        companion object {
            fun getByByteValue(byteValue: Byte): FrameType? =
                values().firstOrNull { it.byteValue == byteValue }
        }
    }

    companion object : BinaryDeserializer<FrameSOF, BinaryDeserializerFrameContext> {
        const val SIGNATURE = 0x01.toByte()

        override fun getHandledSignatureBytes() = setOf(SIGNATURE)

        private fun calculateChecksum(data: ByteArray): Byte {
            var chksum = 0xff.toByte()
            data.forEach {
                chksum = chksum.xor(it)
            }
            return chksum
        }

        @ExperimentalUnsignedTypes
        override fun deserialize(inputStream: InputStream, context: BinaryDeserializerFrameContext): FrameSOF {
            // First is always the length byte
            val lengthByte = inputStream.getByte()
            val length = lengthByte.toUByte().toInt()

            // Next byte is the frame type (REQ/RES)
            val frameTypeByte = inputStream.getByte()
            val frameType = FrameType.getByByteValue(frameTypeByte) ?: throw IOException("Invalid frame type byte!")


            // The value of the Length Field MUST NOT include the SOF
            // and Checksum fields.
            // On the other hand the we already removed length and type
            // byte from buffer, so we should decrease the size by 2
            val data = inputStream.readNBytes(length - 2)


            // Now create data for checksum calculation
            // This buffer should contain the length field and data fields
            var dataForChecksum = ByteArray(0)
            dataForChecksum += lengthByte
            dataForChecksum += frameTypeByte
            dataForChecksum += data

            // Get checksum byte from input stream
            val checksumByte = inputStream.getByte()

            // Calculate checksum for data & compare with checksum byte received from serial
            if (calculateChecksum(dataForChecksum) != checksumByte)
                throw IOException("Invalid checksum received!")

            // Create function from data (data already contains only function data)
            val function = FunctionFactory.deserializeFunction(data.inputStream(), context, frameType)

            // Create new SOF frame
            return FrameSOF(frameType, function)
        }
    }


    @ExperimentalUnsignedTypes
    override fun serialize(outputStream: OutputStream) {
        outputStream.putByte(SIGNATURE)

        var resultData = ByteArray(0)

        val functionOS = ByteArrayOutputStream()
        function.serialize(functionOS, this)

        val functionData = functionOS.toByteArray()

        // Put size byte (function data + length + type)
        val size = functionData.size + 2

        resultData += size.toUByte().toByte()
        resultData += frameType.byteValue
        resultData += functionData

        resultData += calculateChecksum(resultData)

        outputStream.write(resultData)
    }

    override fun toString(): String {
        val typeDescr = when (frameType) {
            FrameType.REQUEST -> "REQ"
            FrameType.RESPONSE -> "RES"
        }

        return "$typeDescr: $function"
    }

}