package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

open class DeserializableFunctionContext(
    network: Network,
    frameID: FrameID,
    val frameType: FrameType,
    val functionID: FunctionID
) : DeserializableFrameContext(network, frameID) {

    override fun getSignatureByte(): Byte = functionID.byteValue

    constructor(frameContext: DeserializableFrameContext, frameType: FrameType, functionID: FunctionID) : this(
        frameContext.network,
        frameContext.frameID,
        frameType,
        functionID
    )

}
