@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.transport.serialization.BinaryFrameDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFrameContext
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import java.io.InputStream
import java.io.OutputStream

class FrameACK(predecessor: Frame? = null) : FrameState(predecessor) {

    companion object : BinaryFrameDeserializer {
        const val SIGNATURE = 0x06.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override fun deserialize(inputStream: InputStream, context: DeserializableFrameContext): FrameACK = FrameACK()
    }

    override fun serialize(outputStream: OutputStream, context: SerializableFrameContext) =
        outputStream.putByte(SIGNATURE)

    override fun toString() = "ACK"

}