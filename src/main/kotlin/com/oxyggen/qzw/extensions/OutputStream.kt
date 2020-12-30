package com.oxyggen.qzw.extensions

import java.io.OutputStream

fun OutputStream.putByte(b: Byte) {
    val ba = ByteArray(1)
    ba[0] = b
    write(ba)
}

fun OutputStream.putByteForced(b: Byte?) {
    putByte(b ?: 0x00)
}

@ExperimentalUnsignedTypes
fun OutputStream.putUByte(b: UByte) {
    putByte(b.toByte())
}

@ExperimentalUnsignedTypes
fun OutputStream.putUByteForced(b: UByte?) {
    putUByte(b ?: 0u)
}
