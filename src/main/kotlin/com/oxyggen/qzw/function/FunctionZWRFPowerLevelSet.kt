package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRFPowerLevelSet : Function() {
    companion object : BinaryDeserializer<FunctionZWRFPowerLevelSet> {
        const val SIGNATURE = 0x17.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRFPowerLevelSet {
            return FunctionZWRFPowerLevelSet()
        }
    }
}            