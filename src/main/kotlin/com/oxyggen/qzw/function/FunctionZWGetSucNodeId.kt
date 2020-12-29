package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWGetSucNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWGetSucNodeId, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x56.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetSucNodeId {
            return FunctionZWGetSucNodeId()
        }
    }
}            