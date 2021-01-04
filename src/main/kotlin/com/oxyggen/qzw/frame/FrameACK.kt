@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.frame

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.serialization.BinaryFrameDeserializer
import com.oxyggen.qzw.serialization.BinaryFrameDeserializerContext
import java.io.InputStream
import java.io.OutputStream

class FrameACK : FrameState() {

    companion object : BinaryFrameDeserializer {
        const val SIGNATURE = 0x06.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override fun deserialize(inputStream: InputStream, context: BinaryFrameDeserializerContext): FrameACK = FrameACK()
    }

    override fun serialize(outputStream: OutputStream) {
        outputStream.putByte(SIGNATURE)
    }

    override fun toString() = "ACK"

}