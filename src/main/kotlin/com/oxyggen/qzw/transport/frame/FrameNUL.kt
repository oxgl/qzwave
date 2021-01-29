@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.transport.serialization.BinaryFrameDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFrameContext
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import java.io.InputStream
import java.io.OutputStream

class FrameNUL(predecessor: Frame? = null) : FrameState(predecessor) {
    override fun serialize(outputStream: OutputStream, context: SerializableFrameContext) {
        throw NotImplementedError("This frame only for internal usage! Do not serialize!")
    }

    override fun toString() = "[NO RESULT]"
}