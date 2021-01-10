package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.node.NetworkInfoGetter

open class SerializableFunctionContext(networkInfo: NetworkInfoGetter, val frame: FrameSOF) :
    SerializableFrameContext(networkInfo) {
    constructor(frameContext: SerializableFrameContext, frame: FrameSOF) : this(frameContext.networkInfo, frame)
}
