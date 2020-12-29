package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWMemoryGetId : Function() {
    companion object : BinaryDeserializer<FunctionZWMemoryGetId, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x20.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWMemoryGetId {
            return FunctionZWMemoryGetId()
        }
    }
}            