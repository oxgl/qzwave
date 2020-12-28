package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetControllerCapabilities : Function() {
    companion object : BinaryDeserializer<FunctionZWGetControllerCapabilities> {
        const val SIGNATURE = 0x05.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetControllerCapabilities {
            return FunctionZWGetControllerCapabilities()
        }
    }
}            