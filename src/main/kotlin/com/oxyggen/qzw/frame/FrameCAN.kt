@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.frame

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.node.NetworkInfoGetter
import com.oxyggen.qzw.serialization.BinaryFrameDeserializer
import com.oxyggen.qzw.serialization.DeserializableFrameContext
import com.oxyggen.qzw.serialization.SerializableFrameContext
import java.io.InputStream
import java.io.OutputStream

class FrameCAN : FrameState() {

    companion object : BinaryFrameDeserializer {
        const val SIGNATURE = 0x18.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override fun deserialize(inputStream: InputStream, context: DeserializableFrameContext): FrameCAN = FrameCAN()
    }

    override fun serialize(outputStream: OutputStream, context: SerializableFrameContext) =
        outputStream.putByte(SIGNATURE)


    override fun toString() = "CAN"

}