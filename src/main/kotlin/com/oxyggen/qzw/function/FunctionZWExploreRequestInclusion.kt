package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWExploreRequestInclusion : Function() {
    companion object : BinaryDeserializer<FunctionZWExploreRequestInclusion> {
        const val SIGNATURE = 0x5e.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWExploreRequestInclusion {
            return FunctionZWExploreRequestInclusion()
        }
    }
}            