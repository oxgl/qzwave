package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.command.Command

typealias BinaryCommandDeserializer = BinaryDeserializer<Command, BinaryCommandDeserializerContext>
