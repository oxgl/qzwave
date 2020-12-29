package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import java.io.InputStream

class FunctionZWExploreRequestInclusion : Function() {
    companion object : BinaryDeserializer<FunctionZWExploreRequestInclusion, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x5e.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionZWExploreRequestInclusion {
            return FunctionZWExploreRequestInclusion()
        }
    }
}            