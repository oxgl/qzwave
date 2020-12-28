package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSendSlaveNodeInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWSendSlaveNodeInfo> {
        const val SIGNATURE = 0xa2.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSendSlaveNodeInfo {
            return FunctionZWSendSlaveNodeInfo()
        }
    }
}            