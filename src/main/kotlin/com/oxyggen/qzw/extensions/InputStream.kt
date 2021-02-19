package com.oxyggen.qzw.extensions

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.InputStream

fun InputStream.isBlocking() = this !is ByteArrayInputStream

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getByte(): Byte =
    if (isBlocking()) withContext(Dispatchers.IO) { readNBytes(1)[0] } else readNBytes(1)[0]

@OptIn(ExperimentalUnsignedTypes::class)
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getUByte(): UByte =
    if (isBlocking()) withContext(Dispatchers.IO) { readNBytes(1)[0].toUByte() } else readNBytes(1)[0].toUByte()

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getAllBytes(): ByteArray =
    if (isBlocking()) withContext(Dispatchers.IO) { readAllBytes() } else readAllBytes()

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getNBytes(len: Int): ByteArray =
    if (isBlocking()) withContext(Dispatchers.IO) { readNBytes(len) } else readNBytes(len)

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.availableBytes(): Int =
    if (isBlocking()) withContext(Dispatchers.IO) { available() } else available()

