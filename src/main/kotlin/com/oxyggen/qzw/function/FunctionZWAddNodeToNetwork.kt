package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWAddNodeToNetwork : Function() {
    companion object : BinaryDeserializer<FunctionZWAddNodeToNetwork, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x4a.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWAddNodeToNetwork {
            return FunctionZWAddNodeToNetwork()
        }
    }
}            