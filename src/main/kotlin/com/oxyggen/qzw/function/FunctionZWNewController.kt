package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWNewController : Function() {
    companion object : BinaryDeserializer<FunctionZWNewController, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x43.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWNewController {
            return FunctionZWNewController()
        }
    }
}            