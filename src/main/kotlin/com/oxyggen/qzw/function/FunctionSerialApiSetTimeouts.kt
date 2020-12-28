package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiSetTimeouts : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSetTimeouts> {
        const val SIGNATURE = 0x06.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiSetTimeouts {
            return FunctionSerialApiSetTimeouts()
        }
    }
}            