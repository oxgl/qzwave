package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRequestNodeInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNodeInfo> {
        const val SIGNATURE = 0x60.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRequestNodeInfo {
            return FunctionZWRequestNodeInfo()
        }
    }
}            