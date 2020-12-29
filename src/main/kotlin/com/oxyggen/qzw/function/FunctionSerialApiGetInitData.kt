package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import com.oxyggen.qzw.types.LibraryType
import java.io.InputStream

class FunctionSerialApiGetInitData : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiGetInitData, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x02.toByte()

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiGetInitData {
            when (context.frameType) {
                FrameSOF.FrameType.REQUEST -> FunctionSerialApiGetInitData()
                FrameSOF.FrameType.RESPONSE -> {
                    val serialApiVersion = inputStream.getByte()

                    val versionText = inputStream.readNBytes(12).decodeToString()
                    val libraryType = LibraryType.getByByteValue(inputStream.getByte())
                    //FunctionSerialApiGetInitData(versionText,libraryType)
                }
                else -> FunctionZWGetVersion()
            }
            // TODO: Finish!
            return FunctionSerialApiGetInitData()

        }
    }
}            