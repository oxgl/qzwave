package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import java.io.InputStream

class FunctionProprietaryAny : Function() {
    companion object : BinaryDeserializer<FunctionProprietaryAny> {
        override fun getHandledSignatureBytes(): Set<Byte> {
            var result = mutableSetOf<Byte>()
            (0xf0..0xfe).forEach {
                result.add(it.toByte())
            }
            return result
        }

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionProprietaryAny {
            return FunctionProprietaryAny()
        }
    }
}            