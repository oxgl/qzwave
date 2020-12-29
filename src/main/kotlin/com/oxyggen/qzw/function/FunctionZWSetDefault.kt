package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSetDefault : Function() {
    companion object : BinaryDeserializer<FunctionZWSetDefault, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x42.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSetDefault {
            return FunctionZWSetDefault()
        }
    }
}            