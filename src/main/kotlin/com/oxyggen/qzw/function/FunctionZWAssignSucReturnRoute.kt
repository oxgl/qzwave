package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWAssignSucReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWAssignSucReturnRoute, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x51.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWAssignSucReturnRoute {
            return FunctionZWAssignSucReturnRoute()
        }
    }
}            