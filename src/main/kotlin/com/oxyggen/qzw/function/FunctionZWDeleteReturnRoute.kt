package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWDeleteReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWDeleteReturnRoute> {
        const val SIGNATURE = 0x47.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWDeleteReturnRoute {
            return FunctionZWDeleteReturnRoute()
        }
    }
}            