package com.oxyggen.qzw.driver

import java.io.InputStream

interface BinaryDeserializer<T> {
    fun getHandledSignatureBytes(): Set<Byte>

    fun deserialize(signatureByte: Byte, inputStream: InputStream): T
}