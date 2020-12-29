package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionSerialApiSlaveNodeInfo : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSlaveNodeInfo, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0xa0.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionSerialApiSlaveNodeInfo {
            return FunctionSerialApiSlaveNodeInfo()
        }
    }
}            