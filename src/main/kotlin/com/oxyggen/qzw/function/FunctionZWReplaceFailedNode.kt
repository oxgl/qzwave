package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWReplaceFailedNode : Function() {
    companion object : BinaryDeserializer<FunctionZWReplaceFailedNode> {
        const val SIGNATURE = 0x63.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWReplaceFailedNode {
            return FunctionZWReplaceFailedNode()
        }
    }
}            