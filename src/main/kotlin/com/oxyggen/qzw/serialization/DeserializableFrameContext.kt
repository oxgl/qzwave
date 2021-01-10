package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.node.NetworkInfoGetter
import com.oxyggen.qzw.types.FrameID

open class DeserializableFrameContext(
    networkInfo: NetworkInfoGetter,
    val frameID: FrameID
) : DeserializableObjectContext(networkInfo) {
    override fun getSignatureByte(): Byte = frameID.byteValue
}