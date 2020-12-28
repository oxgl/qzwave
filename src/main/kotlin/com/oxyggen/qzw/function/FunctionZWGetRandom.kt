package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetRandom : Function() {
    companion object : BinaryDeserializer<FunctionZWGetRandom> {
        const val SIGNATURE = 0x1c.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetRandom {
            return FunctionZWGetRandom()
        }
    }
}            