package com.oxyggen.qzw.mapper

import com.oxyggen.qzw.extensions.from

class BinaryMapperBitMask(val bitMapVersion: IntRange) {

    val masks: MutableMap<String, BinaryBitMaskDefinition> = mutableMapOf()

    fun bitRange(name: String, range: IntRange, version: IntRange = IntRange.EMPTY) {
        val newVersion =
            if (version != IntRange.EMPTY && bitMapVersion != IntRange.EMPTY) {
                maxOf(version.first, bitMapVersion.first)..minOf(version.last, bitMapVersion.last)
            } else if (version != IntRange.EMPTY) version
            else bitMapVersion

        masks[name] = BinaryBitMaskDefinition(range, newVersion)
    }

    fun bit(name: String, index: Int, version: IntRange = IntRange.EMPTY) {
        bitRange(name, index..index, version)
    }

    fun collectNames(): String {
        var result = "BitMask"
        for (e in masks) {
            if (result.isNotBlank()) result += ";"
            result += "${e.key}(${e.value.range})"
        }
        return result
    }
}