package com.oxyggen.qzw.extensions

import java.io.InputStream

fun InputStream.getByte(): Byte = this.readNBytes(1)[0]

@OptIn(ExperimentalUnsignedTypes::class)
fun InputStream.getUByte(): UByte = this.readNBytes(1)[0].toUByte()
