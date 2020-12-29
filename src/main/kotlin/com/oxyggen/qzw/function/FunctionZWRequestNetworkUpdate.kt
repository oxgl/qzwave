package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRequestNetworkUpdate : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNetworkUpdate, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x53.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRequestNetworkUpdate {
            return FunctionZWRequestNetworkUpdate()
        }
    }
}            