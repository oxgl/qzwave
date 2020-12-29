package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionPromiscuousApplicationCommandHandler : Function() {
    companion object : BinaryDeserializer<FunctionPromiscuousApplicationCommandHandler, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xd1.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionPromiscuousApplicationCommandHandler {
            return FunctionPromiscuousApplicationCommandHandler()
        }
    }
}            