package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSendNodeInformation : Function() {
    companion object : BinaryDeserializer<FunctionZWSendNodeInformation, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x12.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSendNodeInformation {
            return FunctionZWSendNodeInformation()
        }
    }
}            