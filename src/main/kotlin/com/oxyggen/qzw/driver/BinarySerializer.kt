package com.oxyggen.qzw.driver

import java.io.OutputStream

interface BinarySerializer {
    fun serialize(outputStream: OutputStream)
}