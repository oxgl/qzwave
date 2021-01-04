package com.oxyggen.qzw.mapper

interface BinaryValueGetter {
    fun getStringValue(byteArray: ByteArray, expression: String): String?
}