package com.oxyggen.qzw.serialization

import java.io.InputStream

interface BinaryDeserializer<T, C : BinaryDeserializerContext> {

    fun getHandledSignatureBytes(): Set<Byte>

    fun deserialize(inputStream: InputStream, context: C): T
}