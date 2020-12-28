package com.oxyggen.qzw.frame

import com.oxyggen.qzw.driver.BinaryDeserializer
import com.oxyggen.qzw.extensions.putByte
import java.io.InputStream
import java.io.OutputStream

class FrameNAK : FrameState() {

    companion object : BinaryDeserializer<FrameNAK> {
        const val SIGNATURE = 0x15.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FrameNAK = FrameNAK()
    }

    override fun serialize(outputStream: OutputStream) {
        outputStream.putByte(FrameACK.SIGNATURE)
    }

    override fun toString() = "NAK"

}