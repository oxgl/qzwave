package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetVersion : Function() {
    companion object : BinaryDeserializer<FunctionZWGetVersion> {
        const val SIGNATURE = 0x15.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetVersion {
            return FunctionZWGetVersion()
        }
    }
}            