package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWEnableSuc : Function() {
    companion object : BinaryDeserializer<FunctionZWEnableSuc> {
        const val SIGNATURE = 0x52.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWEnableSuc {
            return FunctionZWEnableSuc()
        }
    }
}            