package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionMemoryGetByte : Function() {
    companion object : BinaryDeserializer<FunctionMemoryGetByte> {
        const val SIGNATURE = 0x21.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionMemoryGetByte {
            return FunctionMemoryGetByte()
        }
    }
}            