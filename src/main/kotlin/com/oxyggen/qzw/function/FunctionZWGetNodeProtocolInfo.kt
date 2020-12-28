package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetNodeProtocolInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWGetNodeProtocolInfo> {
        const val SIGNATURE = 0x41.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetNodeProtocolInfo {
            return FunctionZWGetNodeProtocolInfo()
        }
    }
}            