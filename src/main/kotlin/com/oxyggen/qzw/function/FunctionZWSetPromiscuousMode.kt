package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSetPromiscuousMode : Function() {
    companion object : BinaryDeserializer<FunctionZWSetPromiscuousMode, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xd0.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSetPromiscuousMode {
            return FunctionZWSetPromiscuousMode()
        }
    }
}            