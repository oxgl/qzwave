package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionMemoryGetByte : Function() {
    companion object : BinaryDeserializer<FunctionMemoryGetByte, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x21.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionMemoryGetByte {
            return FunctionMemoryGetByte()
        }
    }
}            