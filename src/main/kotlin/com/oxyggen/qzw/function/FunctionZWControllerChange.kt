package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWControllerChange : Function() {
    companion object : BinaryDeserializer<FunctionZWControllerChange, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x4d.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWControllerChange {
            return FunctionZWControllerChange()
        }
    }
}            