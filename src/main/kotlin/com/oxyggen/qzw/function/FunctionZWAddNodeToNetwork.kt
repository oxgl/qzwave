package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWAddNodeToNetwork : Function() {
    companion object : BinaryDeserializer<FunctionZWAddNodeToNetwork> {
        const val SIGNATURE = 0x4a.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWAddNodeToNetwork {
            return FunctionZWAddNodeToNetwork()
        }
    }
}            