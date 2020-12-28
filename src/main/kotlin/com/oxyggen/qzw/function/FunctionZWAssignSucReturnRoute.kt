package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWAssignSucReturnRoute : Function() {
    companion object : BinaryDeserializer<FunctionZWAssignSucReturnRoute> {
        const val SIGNATURE = 0x51.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWAssignSucReturnRoute {
            return FunctionZWAssignSucReturnRoute()
        }
    }
}            