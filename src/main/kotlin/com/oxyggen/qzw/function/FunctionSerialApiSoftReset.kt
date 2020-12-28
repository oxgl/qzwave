package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiSoftReset : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSoftReset> {
        const val SIGNATURE = 0x08.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiSoftReset {
            return FunctionSerialApiSoftReset()
        }
    }
}            