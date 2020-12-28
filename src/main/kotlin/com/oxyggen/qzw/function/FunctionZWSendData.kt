package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSendData : Function() {
    companion object : BinaryDeserializer<FunctionZWSendData> {
        const val SIGNATURE = 0x13.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSendData {
            return FunctionZWSendData()
        }
    }
}            