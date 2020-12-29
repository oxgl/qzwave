package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWGetVirtualNodes : Function() {
    companion object : BinaryDeserializer<FunctionZWGetVirtualNodes, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa5.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetVirtualNodes {
            return FunctionZWGetVirtualNodes()
        }
    }
}            