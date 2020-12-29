package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRequestNodeNeighborUpdate : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNodeNeighborUpdate, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x48.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRequestNodeNeighborUpdate {
            return FunctionZWRequestNodeNeighborUpdate()
        }
    }
}            