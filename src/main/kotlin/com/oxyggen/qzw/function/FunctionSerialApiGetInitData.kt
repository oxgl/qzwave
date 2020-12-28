package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiGetInitData : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiGetInitData> {
        const val SIGNATURE = 0x02.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiGetInitData {
            return FunctionSerialApiGetInitData()
        }
    }
}            