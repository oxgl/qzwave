package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSetLearnMode : Function() {
    companion object : BinaryDeserializer<FunctionZWSetLearnMode> {
        const val SIGNATURE = 0x50.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSetLearnMode {
            return FunctionZWSetLearnMode()
        }
    }
}            