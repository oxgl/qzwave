package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSetSucNodeId : Function() {
    companion object : BinaryDeserializer<FunctionZWSetSucNodeId> {
        const val SIGNATURE = 0x54.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSetSucNodeId {
            return FunctionZWSetSucNodeId()
        }
    }
}            