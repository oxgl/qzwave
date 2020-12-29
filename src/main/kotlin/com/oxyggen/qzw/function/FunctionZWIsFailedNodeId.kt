package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWIsFailedNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWIsFailedNodeId, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x62.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWIsFailedNodeId {
            return FunctionZWIsFailedNodeId()
        }
    }
}            