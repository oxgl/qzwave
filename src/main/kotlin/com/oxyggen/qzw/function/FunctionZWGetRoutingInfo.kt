package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWGetRoutingInfo : Function() {
    companion object : BinaryDeserializer<FunctionZWGetRoutingInfo> {
        const val SIGNATURE = 0x80.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWGetRoutingInfo {
            return FunctionZWGetRoutingInfo()
        }
    }
}            