package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSetDefault : Function() {
    companion object : BinaryDeserializer<FunctionZWSetDefault> {
        const val SIGNATURE = 0x42.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSetDefault {
            return FunctionZWSetDefault()
        }
    }
}            