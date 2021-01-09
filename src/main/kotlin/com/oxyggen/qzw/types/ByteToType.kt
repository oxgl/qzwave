package com.oxyggen.qzw.types

interface ByteToType<E> {
    fun getByByteValue(byteValue: Byte): E?
}