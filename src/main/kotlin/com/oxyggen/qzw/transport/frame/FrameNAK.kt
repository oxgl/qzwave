@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.transport.serialization.BinaryFrameDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFrameContext
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import java.io.InputStream
import java.io.OutputStream

class FrameNAK(network: Network, predecessor: Frame? = null) : FrameState(network, predecessor) {

    companion object : BinaryFrameDeserializer {
        const val SIGNATURE = 0x15.toByte()
        override fun getHandledSignatureBytes() = setOf(SIGNATURE)
        override suspend fun deserialize(inputStream: InputStream, context: DeserializableFrameContext): FrameNAK =
            FrameNAK(context.network)
    }

    override suspend fun serialize(outputStream: OutputStream, context: SerializableFrameContext) =
        outputStream.putByte(FrameACK.SIGNATURE)

    override fun toString() = "NAK"

}