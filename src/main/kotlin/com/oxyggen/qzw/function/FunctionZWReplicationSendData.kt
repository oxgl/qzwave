package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWReplicationSendData : Function() {
    companion object : BinaryDeserializer<FunctionZWReplicationSendData> {
        const val SIGNATURE = 0x45.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWReplicationSendData {
            return FunctionZWReplicationSendData()
        }
    }
}            