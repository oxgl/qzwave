package com.oxyggen.qzw.mapper

class BinaryMapperBitMask {
    val masks: MutableMap<String, IntRange> = mutableMapOf()

    fun bitRange(name: String, range: IntRange) {
        masks[name] = range
    }

    fun bit(name: String, index: Int) {
        bitRange(name, index..index)
    }

    fun collectNames(): String {
        var result = "BitMask"
        for (e in masks) {
            if (result.isNotBlank()) result += ";"
            result += "${e.key}(${e.value})"
        }
        return result
    }
}