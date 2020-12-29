package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWAssignReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWAssignReturnRoute, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x46.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWAssignReturnRoute {
            return FunctionZWAssignReturnRoute()
        }
    }
}            