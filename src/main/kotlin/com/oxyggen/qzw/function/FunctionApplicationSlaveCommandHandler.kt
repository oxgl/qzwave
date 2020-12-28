package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionApplicationSlaveCommandHandler : Function() {
    companion object : BinaryDeserializer<FunctionApplicationSlaveCommandHandler> {
        const val SIGNATURE = 0xa1.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionApplicationSlaveCommandHandler {
            return FunctionApplicationSlaveCommandHandler()
        }
    }
}            