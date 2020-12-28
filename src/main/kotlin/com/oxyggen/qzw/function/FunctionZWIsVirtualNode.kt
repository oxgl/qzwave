package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWIsVirtualNode : Function() {
    companion object : BinaryDeserializer<FunctionZWIsVirtualNode> {
        const val SIGNATURE = 0xa6.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWIsVirtualNode {
            return FunctionZWIsVirtualNode()
        }
    }
}            