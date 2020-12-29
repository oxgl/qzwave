package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWReplicationCommandComplete : Function() {
    companion object : BinaryDeserializer<FunctionZWReplicationCommandComplete, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x44.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWReplicationCommandComplete {
            return FunctionZWReplicationCommandComplete()
        }
    }
}            