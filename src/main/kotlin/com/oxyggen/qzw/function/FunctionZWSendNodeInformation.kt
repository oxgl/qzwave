package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSendNodeInformation : Function() {
    companion object : BinaryDeserializer<FunctionZWSendNodeInformation> {
        const val SIGNATURE = 0x12.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSendNodeInformation {
            return FunctionZWSendNodeInformation()
        }
    }
}            