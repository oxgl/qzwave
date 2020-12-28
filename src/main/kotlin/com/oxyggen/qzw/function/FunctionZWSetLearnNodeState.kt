package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWSetLearnNodeState : Function() {
    companion object : BinaryDeserializer<FunctionZWSetLearnNodeState> {
        const val SIGNATURE = 0x40.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWSetLearnNodeState {
            return FunctionZWSetLearnNodeState()
        }
    }
}            