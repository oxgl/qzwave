package com.oxyggen.qzw.serialization

import java.io.OutputStream

interface BinarySerializer {
    fun serialize(outputStream: OutputStream)
}