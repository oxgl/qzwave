package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWGetRandom : Function() {
    companion object : BinaryDeserializer<FunctionZWGetRandom, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x1c.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetRandom {
            return FunctionZWGetRandom()
        }
    }
}            