package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetSucNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWGetSucNodeId> {
        const val SIGNATURE = 0x56.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetSucNodeId {
            return FunctionZWGetSucNodeId()
        }
    }
}            