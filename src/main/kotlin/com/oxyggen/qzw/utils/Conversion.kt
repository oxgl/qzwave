package com.oxyggen.qzw.utils

import com.oxyggen.qzw.types.TypeToByte
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class Conversion {
    companion object {
        fun toInt(value: Any): Int = when (value) {
            is Char -> value.toInt()
            is Number -> value.toInt()
            is Boolean -> if (value) 1 else 0
            is String -> if (value.startsWith("0x")) value.drop(2).toInt(16) else value.toInt()
            is TypeToByte -> value.byteValue.toInt()
            else -> 0
        }

        fun toByte(value: Any): Byte = toInt(value).toByte()

        fun toBoolean(value: Any): Boolean = when (value) {
            is Char -> value == '1' || value.toUpperCase() == 'X'
            is Boolean -> value
            is Number -> value.toInt() != 0
            is String -> value.toLowerCase().trim() == "true"
            else -> false
        }

        fun toString(value: Any): String = when (value) {
            is String -> value
            is ByteArray -> value.toString(StandardCharsets.US_ASCII)
            else -> value.toString()
        }

        @Suppress("UNCHECKED_CAST")
        fun toCollection(value: Any): Collection<Any> = when (value) {
            is Collection<*> -> value as Collection<Any>
            is ByteArray -> value.toList()
            is String -> value.toByteArray(StandardCharsets.US_ASCII).toList()
            else -> listOf(value)
        }

    }
}