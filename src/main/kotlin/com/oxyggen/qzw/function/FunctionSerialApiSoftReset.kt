package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionSerialApiSoftReset : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSoftReset, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x08.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiSoftReset {
            return FunctionSerialApiSoftReset()
        }
    }
}            