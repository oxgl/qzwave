package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSetSlaveLearnMode : Function() {
    companion object : BinaryDeserializer<FunctionZWSetSlaveLearnMode> {
        const val SIGNATURE = 0xa4.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSetSlaveLearnMode {
            return FunctionZWSetSlaveLearnMode()
        }
    }
}            