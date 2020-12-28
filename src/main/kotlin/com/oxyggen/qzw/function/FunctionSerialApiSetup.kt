package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiSetup : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSetup> {
        const val SIGNATURE = 0x0b.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiSetup {
            return FunctionSerialApiSetup()
        }
    }
}            