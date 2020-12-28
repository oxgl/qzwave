package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWDeleteSucReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWDeleteSucReturnRoute> {
        const val SIGNATURE = 0x55.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWDeleteSucReturnRoute {
            return FunctionZWDeleteSucReturnRoute()
        }
    }
}            