@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import java.io.OutputStream

/**
 * Internal frame: Synchronize node list
 */
class FrameSYN internal constructor(network: Network, predecessor: Frame? = null) : FrameState(network, predecessor) {
    override suspend fun serialize(outputStream: OutputStream, context: SerializableFrameContext) {
        throw NotImplementedError("This frame only for internal usage! Do not serialize!")
    }

    override fun toString() = "[SYNC NODE LIST]"
}