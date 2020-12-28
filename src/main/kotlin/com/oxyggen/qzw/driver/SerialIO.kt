package com.oxyggen.qzw.driver

import java.io.InputStream
import java.io.OutputStream

interface SerialIO {
    fun deserialize(inputStream: InputStream)
    fun serialize(outputStream: OutputStream)
}