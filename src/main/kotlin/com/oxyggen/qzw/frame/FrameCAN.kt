package com.oxyggen.qzw.frame

import com.oxyggen.qzw.driver.BinaryDeserializer
import com.oxyggen.qzw.extensions.putByte
import java.io.InputStream
import java.io.OutputStream

class FrameCAN : FrameState() {

    companion object : BinaryDeserializer<FrameCAN> {
        const val SIGNATURE = 0x18.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FrameCAN = FrameCAN()
    }

    override fun serialize(outputStream: OutputStream) {
        outputStream.putByte(SIGNATURE)
    }

    override fun toString() = "CAN"

}