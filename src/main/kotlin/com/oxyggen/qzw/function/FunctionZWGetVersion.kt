package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionId
import com.oxyggen.qzw.types.LibraryType
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionZWGetVersion : Function() {


    companion object : BinaryFunctionDeserializer {
        private val SIGNATURE = FunctionId.ZW_GET_VERSION.byteValue

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        // HOST->ZW: REQ | 0x15
        // ZW->HOST: RES | 0x15 | buffer (12 bytes) | library type
        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request()
                FrameType.RESPONSE -> {
                    val versionText = inputStream.readNBytes(12).decodeToString()
                    val libraryType = LibraryType.getByByteValue(inputStream.getByte())
                    Response(versionText, libraryType ?: LibraryType.CONTROLLER_STATIC)
                }
            }
    }

    class Request : FunctionRequest() {

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            outputStream.putByte(SIGNATURE)
        }

        override fun toString(): String {
            return "ZW_GET_VERSION()"
        }
    }

    class Response(val versionText: String, val libraryType: LibraryType) : FunctionResponse() {

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            outputStream.putByte(SIGNATURE)
            outputStream.write(ByteArray(12))
            outputStream.putByte(LibraryType.CONTROLLER_STATIC.byteValue)
        }

        override fun toString(): String {
            return "ZW_GET_VERSION($versionText, $libraryType)"
        }
    }

}