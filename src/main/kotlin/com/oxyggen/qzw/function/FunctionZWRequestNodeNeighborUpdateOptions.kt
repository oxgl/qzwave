package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRequestNodeNeighborUpdateOptions : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNodeNeighborUpdateOptions, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x5a.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRequestNodeNeighborUpdateOptions {
            return FunctionZWRequestNodeNeighborUpdateOptions()
        }
    }
}            