package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWIsVirtualNode : Function() {
    companion object : BinaryDeserializer<FunctionZWIsVirtualNode, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa6.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWIsVirtualNode {
            return FunctionZWIsVirtualNode()
        }
    }
}            