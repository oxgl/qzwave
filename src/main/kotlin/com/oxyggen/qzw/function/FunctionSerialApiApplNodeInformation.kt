package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionSerialApiApplNodeInformation : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiApplNodeInformation, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x03.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiApplNodeInformation {
            return FunctionSerialApiApplNodeInformation()
        }
    }
}            