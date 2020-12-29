package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWCreateNewPrimary : Function() {
    companion object : BinaryDeserializer<FunctionZWCreateNewPrimary, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x4c.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWCreateNewPrimary {
            return FunctionZWCreateNewPrimary()
        }
    }
}            