package com.oxyggen.qzw.extensions

import com.oxyggen.qzw.types.TypeToByte
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun OutputStream.putByte(b: Byte) {
    val ba = ByteArray(1)
    ba[0] = b
    write(ba)
}

suspend fun OutputStream.putByteForced(b: Byte?) = putByte(b ?: 0x00)

@ExperimentalUnsignedTypes
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun OutputStream.putUByte(b: UByte) = putByte(b.toByte())

@ExperimentalUnsignedTypes
suspend fun OutputStream.putUByteForced(b: UByte?) = putUByte(b ?: 0u)

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun OutputStream.putBytes(byteArray: ByteArray) = write(byteArray)


suspend fun OutputStream.put(serializable: TypeToByte) = putByte(serializable.byteValue)



