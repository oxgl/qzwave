package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWCreateNewPrimary : Function() {
    companion object : BinaryDeserializer<FunctionZWCreateNewPrimary> {
        const val SIGNATURE = 0x4c.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWCreateNewPrimary {
            return FunctionZWCreateNewPrimary()
        }
    }
}            