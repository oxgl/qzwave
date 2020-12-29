package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSetSlaveLearnMode : Function() {
    companion object : BinaryDeserializer<FunctionZWSetSlaveLearnMode, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa4.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSetSlaveLearnMode {
            return FunctionZWSetSlaveLearnMode()
        }
    }
}            