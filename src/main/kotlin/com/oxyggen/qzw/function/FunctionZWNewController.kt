package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWNewController : Function() {
    companion object : BinaryDeserializer<FunctionZWNewController> {
        const val SIGNATURE = 0x43.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWNewController {
            return FunctionZWNewController()
        }
    }
}            