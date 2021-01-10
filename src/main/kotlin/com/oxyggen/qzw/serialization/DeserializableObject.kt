package com.oxyggen.qzw.serialization

import java.io.InputStream

interface DeserializableObject<T, C : DeserializableObjectContext> {

    fun getHandledSignatureBytes(): Set<Byte>

    fun deserialize(inputStream: InputStream, context: C): T
}