package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSetPromiscuousMode : Function() {
    companion object : BinaryDeserializer<FunctionZWSetPromiscuousMode> {
        const val SIGNATURE = 0xd0.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSetPromiscuousMode {
            return FunctionZWSetPromiscuousMode()
        }
    }
}            