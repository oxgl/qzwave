package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWRFPowerLevelSet : Function() {
    companion object : BinaryDeserializer<FunctionZWRFPowerLevelSet, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x17.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWRFPowerLevelSet {
            return FunctionZWRFPowerLevelSet()
        }
    }
}            