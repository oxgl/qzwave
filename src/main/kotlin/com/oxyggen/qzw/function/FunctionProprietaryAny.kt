package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionProprietaryAny : Function() {
    companion object : BinaryDeserializer<FunctionProprietaryAny, BinaryDeserializerFunctionContext> {
        override fun getHandledSignatureBytes(): Set<Byte> {
            val result = mutableSetOf<Byte>()
            (0xf0..0xfe).forEach {
                result.add(it.toByte())
            }
            return result
        }

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionProprietaryAny {
            return FunctionProprietaryAny()
        }
    }
}            