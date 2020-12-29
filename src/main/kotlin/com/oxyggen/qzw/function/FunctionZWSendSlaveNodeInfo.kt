package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWSendSlaveNodeInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWSendSlaveNodeInfo, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa2.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWSendSlaveNodeInfo {
            return FunctionZWSendSlaveNodeInfo()
        }
    }
}            