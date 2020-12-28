package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRemoveNodeFromNetwork : Function() {
    companion object : BinaryDeserializer<FunctionZWRemoveNodeFromNetwork> {
        const val SIGNATURE = 0x4b.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRemoveNodeFromNetwork {
            return FunctionZWRemoveNodeFromNetwork()
        }
    }
}            