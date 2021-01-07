package com.oxyggen.qzw.types

interface ByteToClass<E> {
    fun getByByteValue(byteValue: Byte): E?
}