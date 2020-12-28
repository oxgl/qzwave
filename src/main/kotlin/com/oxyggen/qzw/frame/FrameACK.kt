package com.oxyggen.qzw.frame

import com.oxyggen.qzw.driver.BinaryDeserializer
import com.oxyggen.qzw.extensions.putByte
import java.io.InputStream
import java.io.OutputStream

class FrameACK : FrameState() {

    companion object : BinaryDeserializer<FrameACK> {
        const val SIGNATURE = 0x06.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FrameACK = FrameACK()
    }

    override fun serialize(outputStream: OutputStream) {
        outputStream.putByte(SIGNATURE)
    }

    override fun toString() = "ACK"

}