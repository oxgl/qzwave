package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSetLearnMode : Function() {
    companion object : BinaryDeserializer<FunctionZWSetLearnMode, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x50.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSetLearnMode {
            return FunctionZWSetLearnMode()
        }
    }
}            