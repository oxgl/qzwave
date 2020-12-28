package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiApplNodeInformation : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiApplNodeInformation> {
        const val SIGNATURE = 0x03.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiApplNodeInformation {
            return FunctionSerialApiApplNodeInformation()
        }
    }
}            