package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionPromiscuousApplicationCommandHandler : Function() {
    companion object : BinaryDeserializer<FunctionPromiscuousApplicationCommandHandler> {
        const val SIGNATURE = 0xd1.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionPromiscuousApplicationCommandHandler {
            return FunctionPromiscuousApplicationCommandHandler()
        }
    }
}            