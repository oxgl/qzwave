package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.transport.frame.Frame

typealias BinaryFrameDeserializer = DeserializableObject<Frame, DeserializableFrameContext>
