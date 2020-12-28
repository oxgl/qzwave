package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionZWRequestNodeNeighborUpdateOptions : Function() {
    companion object : BinaryDeserializer<FunctionZWRequestNodeNeighborUpdateOptions> {
        const val SIGNATURE = 0x5a.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWRequestNodeNeighborUpdateOptions {
            return FunctionZWRequestNodeNeighborUpdateOptions()
        }
    }
}            