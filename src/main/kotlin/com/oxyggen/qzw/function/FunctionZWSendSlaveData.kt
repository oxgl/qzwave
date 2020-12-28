package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSendSlaveData : Function() {
    companion object : BinaryDeserializer<FunctionZWSendSlaveData> {
        const val SIGNATURE = 0xa3.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSendSlaveData {
            return FunctionZWSendSlaveData()
        }
    }
}            