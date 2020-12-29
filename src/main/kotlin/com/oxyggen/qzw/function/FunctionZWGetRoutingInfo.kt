package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWGetRoutingInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWGetRoutingInfo, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x80.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetRoutingInfo {
            return FunctionZWGetRoutingInfo()
        }
    }
}            