package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWReadMemory : Function() {
    companion object : BinaryDeserializer<FunctionZWReadMemory, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x23.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWReadMemory {
            return FunctionZWReadMemory()
        }
    }
}            