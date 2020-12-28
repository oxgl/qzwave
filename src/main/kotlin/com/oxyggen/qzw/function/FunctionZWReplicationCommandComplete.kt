package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWReplicationCommandComplete : Function() {
    companion object : BinaryDeserializer<FunctionZWReplicationCommandComplete> {
        const val SIGNATURE = 0x44.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWReplicationCommandComplete {
            return FunctionZWReplicationCommandComplete()
        }
    }
}            