package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.extensions.getAllBytes
import com.oxyggen.qzw.extensions.putBytes
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.LibraryType
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionZWGetVersion {

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_VERSION.byteValue)

        // HOST->ZW: REQ | 0x15
        // ZW->HOST: RES | 0x15 | buffer (12 bytes) | library type
        override suspend fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    /************************************************************************************
     * HOST->ZW: REQ | 0x15
     ************************************************************************************/
    class Request : FunctionRequest(FunctionID.ZW_GET_VERSION) {
        companion object {
            fun deserialize(inputStream: InputStream): Request = Request()
        }
    }


    /************************************************************************************
     * ZW->HOST: RES | 0x15 | buffer (12 bytes) | library type
     ************************************************************************************/
    class Response(
        val versionText: String,
        val libraryType: LibraryType
    ) : FunctionResponse(FunctionID.ZW_GET_VERSION) {

        companion object {
            private val mapper by lazy {
                mapper<Response> {
                    string("versionText", "12")
                    byte("libraryType")
                }
            }

            suspend fun deserialize(inputStream: InputStream): Response = mapper.deserialize(inputStream.getAllBytes())

        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this))
        }

        override fun toString(): String {
            return "$functionID('$versionText', $libraryType)"
        }
    }

}