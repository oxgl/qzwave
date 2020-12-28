package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRemoveFailedNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWRemoveFailedNodeId> {
        const val SIGNATURE = 0x61.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRemoveFailedNodeId {
            return FunctionZWRemoveFailedNodeId()
        }
    }
}            