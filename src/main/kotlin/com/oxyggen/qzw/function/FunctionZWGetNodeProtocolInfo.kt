package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWGetNodeProtocolInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWGetNodeProtocolInfo, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x41.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWGetNodeProtocolInfo {
            return FunctionZWGetNodeProtocolInfo()
        }
    }
}            