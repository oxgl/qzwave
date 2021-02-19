package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.types.FrameID

open class DeserializableFrameContext(
    network: Network,
    val frameID: FrameID
) : DeserializableObjectContext(network) {
    override fun getSignatureByte(): Byte = frameID.byteValue
}