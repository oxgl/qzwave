package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionSerialApiSetup : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSetup, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x0b.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiSetup {
            return FunctionSerialApiSetup()
        }
    }
}            