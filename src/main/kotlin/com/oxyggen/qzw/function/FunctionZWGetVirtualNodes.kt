package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetVirtualNodes : Function() {
    companion object : BinaryDeserializer<FunctionZWGetVirtualNodes> {
        const val SIGNATURE = 0xa5.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetVirtualNodes {
            return FunctionZWGetVirtualNodes()
        }
    }
}            