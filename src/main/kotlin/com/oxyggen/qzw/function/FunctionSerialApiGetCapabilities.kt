package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionSerialApiGetCapabilities : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiGetCapabilities, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x07.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiGetCapabilities {
            return FunctionSerialApiGetCapabilities()
        }
    }
}            