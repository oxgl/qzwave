package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWReplicationSendData : Function() {
    companion object : BinaryDeserializer<FunctionZWReplicationSendData, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x45.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWReplicationSendData {
            return FunctionZWReplicationSendData()
        }
    }
}            