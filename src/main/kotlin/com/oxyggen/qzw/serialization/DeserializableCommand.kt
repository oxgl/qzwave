package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.command.Command

@OptIn(kotlin.ExperimentalUnsignedTypes::class)
typealias CommandDeserializer = DeserializableObject<Command, DeserializableCommandContext>
