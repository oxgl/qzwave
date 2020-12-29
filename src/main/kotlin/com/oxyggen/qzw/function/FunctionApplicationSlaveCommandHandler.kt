package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionApplicationSlaveCommandHandler : Function() {
    companion object : BinaryDeserializer<FunctionApplicationSlaveCommandHandler, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa1.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionApplicationSlaveCommandHandler {
            return FunctionApplicationSlaveCommandHandler()
        }
    }
}            