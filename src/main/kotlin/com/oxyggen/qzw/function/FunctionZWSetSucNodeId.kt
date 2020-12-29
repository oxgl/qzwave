package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSetSucNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWSetSucNodeId, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x54.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSetSucNodeId {
            return FunctionZWSetSucNodeId()
        }
    }
}            