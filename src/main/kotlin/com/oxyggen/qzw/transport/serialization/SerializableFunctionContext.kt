package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.engine.network.NetworkInfoGetter

open class SerializableFunctionContext(networkInfo: NetworkInfoGetter, val frame: FrameSOF) :
    SerializableFrameContext(networkInfo) {
    constructor(frameContext: SerializableFrameContext, frame: FrameSOF) : this(frameContext.networkInfo, frame)
}
