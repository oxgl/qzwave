package com.oxyggen.qzw.transport.serialization

import java.io.InputStream

interface DeserializableObject<T, C : DeserializableObjectContext> {

    fun getHandledSignatureBytes(): Set<Byte>

    suspend fun deserialize(inputStream: InputStream, context: C): T
}