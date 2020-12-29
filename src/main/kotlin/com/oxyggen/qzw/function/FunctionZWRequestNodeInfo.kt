package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRequestNodeInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNodeInfo, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x60.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRequestNodeInfo {
            return FunctionZWRequestNodeInfo()
        }
    }
}            