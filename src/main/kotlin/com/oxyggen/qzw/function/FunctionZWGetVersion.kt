package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import com.oxyggen.qzw.types.LibraryType
import java.io.InputStream
import java.io.OutputStream

class FunctionZWGetVersion(val versionText: String? = null, val libraryType: LibraryType? = null) : Function() {


    companion object : BinaryDeserializer<FunctionZWGetVersion, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x15.toByte()

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        // HOST->ZW: REQ | 0x15
        // ZW->HOST: RES | 0x15 | buffer (12 bytes) | library type
        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetVersion =
            when (context.frameType) {
                FrameSOF.FrameType.REQUEST -> FunctionZWGetVersion()
                FrameSOF.FrameType.RESPONSE -> {
                    val versionText = inputStream.readNBytes(12).decodeToString()
                    val libraryType = LibraryType.getByByteValue(inputStream.getByte())
                    FunctionZWGetVersion(versionText,libraryType)
                }
                else -> FunctionZWGetVersion()
            }
    }

    override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
        outputStream.putByte(SIGNATURE)
        if (frame.frameType == FrameSOF.FrameType.RESPONSE) {
            outputStream.write(ByteArray(12))
            outputStream.putByte(LibraryType.CONTROLLER_STATIC.byteValue)
        }
    }

    override fun toString(): String {
        return "ZW_GET_VERSION($versionText, $libraryType)"
    }
}