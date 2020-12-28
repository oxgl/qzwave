package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWAssignReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWAssignReturnRoute> {
        const val SIGNATURE = 0x46.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWAssignReturnRoute {
            return FunctionZWAssignReturnRoute()
        }
    }
}            