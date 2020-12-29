package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWReplaceFailedNode : Function() {
    companion object : BinaryDeserializer<FunctionZWReplaceFailedNode, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x63.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWReplaceFailedNode {
            return FunctionZWReplaceFailedNode()
        }
    }
}            