package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionSerialApiSlaveNodeInfo : Function() {
    companion object : BinaryDeserializer<FunctionSerialApiSlaveNodeInfo> {
        const val SIGNATURE = 0xa0.toByte()
    
        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionSerialApiSlaveNodeInfo {
            return FunctionSerialApiSlaveNodeInfo()
        }
    }
}            