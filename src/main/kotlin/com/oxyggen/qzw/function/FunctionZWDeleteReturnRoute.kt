package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWDeleteReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWDeleteReturnRoute, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x47.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWDeleteReturnRoute {
            return FunctionZWDeleteReturnRoute()
        }
    }
}            