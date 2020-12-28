package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWControllerChange : Function() {
    companion object : BinaryDeserializer<FunctionZWControllerChange> {
        const val SIGNATURE = 0x4d.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWControllerChange {
            return FunctionZWControllerChange()
        }
    }
}            