package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRemoveFailedNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWRemoveFailedNodeId, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x61.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRemoveFailedNodeId {
            return FunctionZWRemoveFailedNodeId()
        }
    }
}            