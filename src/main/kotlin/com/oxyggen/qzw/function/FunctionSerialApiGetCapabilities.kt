package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiGetCapabilities : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiGetCapabilities> {
        const val SIGNATURE = 0x07.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiGetCapabilities {
            return FunctionSerialApiGetCapabilities()
        }
    }
}            