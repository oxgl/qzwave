package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.LibraryType
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionZWGetVersion {

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_VERSION.byteValue)

        // HOST->ZW: REQ | 0x15
        // ZW->HOST: RES | 0x15 | buffer (12 bytes) | library type
        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    class Request : FunctionRequest(FunctionID.ZW_GET_VERSION) {
        companion object {
            fun deserialize(inputStream: InputStream): Request = Request()
        }
    }

    class Response(
        val versionText: String,
        val libraryType: LibraryType
    ) : FunctionResponse(FunctionID.ZW_GET_VERSION) {

        companion object {
            fun deserialize(inputStream: InputStream): Response {
                val versionText = inputStream.readNBytes(12).decodeToString()
                val libraryType = LibraryType.getByByteValue(inputStream.getByte())
                return Response(versionText, libraryType ?: LibraryType.CONTROLLER_STATIC)
            }
        }

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.write(ByteArray(12))
            outputStream.putByte(LibraryType.CONTROLLER_STATIC.byteValue)
        }

        override fun toString(): String {
            return "$functionId(out '$versionText', out $libraryType)"
        }
    }

}