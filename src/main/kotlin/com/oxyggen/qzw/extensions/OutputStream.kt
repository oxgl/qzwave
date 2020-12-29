package com.oxyggen.qzw.extensions

import java.io.OutputStream

fun OutputStream.putByte(b: Byte) {
    val ba = ByteArray(1)
    ba[0] = b
    this.write(ba)
}
fun OutputStream.putUByte(b: UByte) {
    this.putByte(b.toByte())
}