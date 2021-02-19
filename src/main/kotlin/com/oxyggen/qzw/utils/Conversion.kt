package com.oxyggen.qzw.utils

import com.oxyggen.qzw.types.TypeToByte
import java.nio.charset.Charset

@OptIn(ExperimentalUnsignedTypes::class)
class Conversion {
    companion object {
        fun toInt(value: Any): Int = when (value) {
            is Char -> value.toInt()
            is Number -> value.toInt()
            is UByte -> value.toInt()
            is UInt -> value.toInt()
            is UShort -> value.toInt()
            is ULong -> value.toInt()
            is Boolean -> if (value) 1 else 0
            is String -> if (value.startsWith("0x")) value.drop(2).toInt(16) else value.toInt()
            is TypeToByte -> value.byteValue.toInt()
            else -> 0
        }

        fun toByte(value: Any): Byte = toInt(value).toByte()

        fun toBoolean(value: Any): Boolean = when (value) {
            is Char -> value == '1' || value.toUpperCase() == 'X'
            is Boolean -> value
            is String -> value.toLowerCase().trim() == "true"
            else -> toInt(value) != 0
        }

        fun toString(value: Any, charset: Charset = Charsets.UTF_8): String = when (value) {
            is String -> value
            is ByteArray -> value.toString(charset)
            else -> value.toString()
        }

        @Suppress("UNCHECKED_CAST")
        fun toCollection(value: Any, charset: Charset = Charsets.UTF_8): Collection<Any> = when (value) {
            is Collection<*> -> value as Collection<Any>
            is ByteArray -> value.toList()
            is String -> value.toByteArray(charset).toList()
            else -> listOf(value)
        }

    }
}