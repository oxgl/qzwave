package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWDeleteSucReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWDeleteSucReturnRoute, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x55.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWDeleteSucReturnRoute {
            return FunctionZWDeleteSucReturnRoute()
        }
    }
}            