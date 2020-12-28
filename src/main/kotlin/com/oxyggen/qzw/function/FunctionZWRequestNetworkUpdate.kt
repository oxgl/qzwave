package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRequestNetworkUpdate : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNetworkUpdate> {
        const val SIGNATURE = 0x53.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRequestNetworkUpdate {
            return FunctionZWRequestNetworkUpdate()
        }
    }
}            