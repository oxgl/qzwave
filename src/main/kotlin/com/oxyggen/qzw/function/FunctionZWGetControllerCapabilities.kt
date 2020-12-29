package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWGetControllerCapabilities : Function() {
    companion object : BinaryDeserializer<FunctionZWGetControllerCapabilities, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x05.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetControllerCapabilities {
            return FunctionZWGetControllerCapabilities()
        }
    }
}            