package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRemoveNodeFromNetwork : Function() {
    companion object : BinaryDeserializer<FunctionZWRemoveNodeFromNetwork, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x4b.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRemoveNodeFromNetwork {
            return FunctionZWRemoveNodeFromNetwork()
        }
    }
}            