package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRequestNodeNeighborUpdate : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNodeNeighborUpdate> {
        const val SIGNATURE = 0x48.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRequestNodeNeighborUpdate {
            return FunctionZWRequestNodeNeighborUpdate()
        }
    }
}            