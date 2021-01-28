package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.NetworkInfoGetter
import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

open class DeserializableFunctionContext(
    networkInfo: NetworkInfoGetter,
    frameID: FrameID,
    val frameType: FrameType,
    val functionID: FunctionID
) : DeserializableFrameContext(networkInfo, frameID) {

    override fun getSignatureByte(): Byte = functionID.byteValue

    constructor(frameContext: DeserializableFrameContext, frameType: FrameType, functionID: FunctionID) : this(
        frameContext.networkInfo,
        frameContext.frameID,
        frameType,
        functionID
    )

}
