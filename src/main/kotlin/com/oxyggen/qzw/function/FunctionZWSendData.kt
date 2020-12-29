package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSendData : Function() {
    companion object : BinaryDeserializer<FunctionZWSendData, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x13.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSendData {
            return FunctionZWSendData()
        }
    }
}            