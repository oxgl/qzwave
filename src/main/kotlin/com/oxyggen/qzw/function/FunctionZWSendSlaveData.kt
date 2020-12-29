package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSendSlaveData : Function() {
    companion object : BinaryDeserializer<FunctionZWSendSlaveData, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa3.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSendSlaveData {
            return FunctionZWSendSlaveData()
        }
    }
}            