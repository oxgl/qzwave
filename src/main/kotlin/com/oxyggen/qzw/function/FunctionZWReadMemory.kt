package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWReadMemory : Function() {
    companion object : BinaryDeserializer<FunctionZWReadMemory> {
        const val SIGNATURE = 0x23.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWReadMemory {
            return FunctionZWReadMemory()
        }
    }
}            