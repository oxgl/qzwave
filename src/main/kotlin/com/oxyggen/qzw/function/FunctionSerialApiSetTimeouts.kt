package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionSerialApiSetTimeouts : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSetTimeouts, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x06.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiSetTimeouts {
            return FunctionSerialApiSetTimeouts()
        }
    }
}            