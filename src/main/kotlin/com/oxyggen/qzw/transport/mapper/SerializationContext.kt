package com.oxyggen.qzw.transport.mapper

class SerializationContext(
    val binary: MutableList<Byte> = mutableListOf(),
    val version: Int = 1,
    val values: MutableMap<String, Any> = mutableMapOf(),
    val buffer: MutableMap<String, Any> = mutableMapOf()
)