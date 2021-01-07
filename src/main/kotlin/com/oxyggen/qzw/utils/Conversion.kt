package com.oxyggen.qzw.utils

class Conversion {
    companion object {
        fun toInt(value: Any): Int = when (value) {
            is Number -> value.toInt()
            is Boolean -> if (value) 1 else 0
            is String -> if (value.startsWith("0x")) value.drop(2).toInt(16) else value.toInt()
            else -> 0
        }

        fun toByte(value: Any): Byte = toInt(value).toByte()

        fun toBoolean(value: Any): Boolean = when (value) {
            is Boolean -> value
            is Number -> value.toInt() != 0
            is String -> value.toLowerCase().trim() == "true"
            else -> false
        }

        fun toString(value: Any): String = when (value) {
            is String -> value
            else -> value.toString()
        }

        @Suppress("UNCHECKED_CAST")
        fun toCollection(value: Any): Collection<Any> = when (value) {
            is Collection<*> -> value as Collection<Any>
            is ByteArray -> value.toList()
            is String -> value.toList()
            else -> listOf(value)
        }

    }
}