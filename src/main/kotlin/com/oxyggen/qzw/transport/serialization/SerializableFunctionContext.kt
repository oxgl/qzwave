package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.transport.frame.FrameSOF

open class SerializableFunctionContext(val frame: FrameSOF) :
    SerializableFrameContext() {
    constructor(frameContext: SerializableFrameContext, frame: FrameSOF) : this(frame)
}
