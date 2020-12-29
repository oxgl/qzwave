package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWEnableSuc : Function() {
    companion object : BinaryDeserializer<FunctionZWEnableSuc, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x52.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWEnableSuc {
            return FunctionZWEnableSuc()
        }
    }
}            