package com.oxyggen.qzw.function

interface ByteToEnum<E> {
    fun getByByteValue(byteValue: Byte): E?
}