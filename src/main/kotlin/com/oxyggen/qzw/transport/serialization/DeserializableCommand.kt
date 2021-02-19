package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.transport.command.Command

@OptIn(ExperimentalUnsignedTypes::class)
typealias CommandDeserializer = DeserializableObject<Command, DeserializableCommandContext>
