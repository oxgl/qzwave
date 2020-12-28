package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWIsFailedNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWIsFailedNodeId> {
        const val SIGNATURE = 0x62.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWIsFailedNodeId {
            return FunctionZWIsFailedNodeId()
        }
    }
}            