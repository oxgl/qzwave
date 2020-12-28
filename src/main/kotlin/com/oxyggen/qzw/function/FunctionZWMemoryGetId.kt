package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWMemoryGetId : Function() {
    companion object : BinaryDeserializer<FunctionZWMemoryGetId> {
        const val SIGNATURE = 0x20.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWMemoryGetId {
            return FunctionZWMemoryGetId()
        }
    }
}            