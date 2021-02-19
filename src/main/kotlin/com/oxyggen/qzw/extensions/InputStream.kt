package com.oxyggen.qzw.extensions

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.coroutines.coroutineContext

@Suppress("BlockingMethodInNonBlockingContext")
fun InputStream.getByte(): Byte = readNBytes(1)[0]

@OptIn(ExperimentalUnsignedTypes::class)
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getUByte(): UByte = readNBytes(1)[0].toUByte()

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getAllBytes(): ByteArray = readAllBytes()

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.getNBytes(len: Int): ByteArray = readNBytes(len)

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun InputStream.availableBytes(): Int = available()

