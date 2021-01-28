package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.NetworkInfoGetter
import com.oxyggen.qzw.types.FrameID

open class DeserializableFrameContext(
    networkInfo: NetworkInfoGetter,
    val frameID: FrameID
) : DeserializableObjectContext(networkInfo) {
    override fun getSignatureByte(): Byte = frameID.byteValue
}