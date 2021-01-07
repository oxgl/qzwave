package com.oxyggen.qzw.extensions

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

operator fun Byte.get(index: Int): Boolean = this.and(1.shl(index).toByte()) == 1.shl(index).toByte()

fun Byte.Companion.build(vararg bits: Boolean): Byte {       // Bits from bit n -> bit 0 (last is the bit 0)
    var result = 0x00.toByte()
    for ((index, b) in bits.reversed().withIndex()) {
        result = result.withBit(index, b)
    }
    return result
}

fun Byte.getBitRange(range: IntRange): Byte {
    var result = 0
    for (index in range.reversed())
        result = result * 2 + if (this[index]) 1 else 0
    return result.toByte()
}

fun Byte.withBitRange(range: IntRange, value: Byte): Byte {
    var result = this
    var value1 = value.toInt()
    for (index in range) {
        result = result.withBit(index, value1.rem(2) > 0)
        value1 = value1.div(2)
    }
    return result
}

fun Byte.withBit(index: Int, value: Boolean = true): Byte = if (this[index] != value) {
    if (value) this.or(1.shl(index).toByte()) else this.xor(1.shl(index).toByte())
} else {
    this
}

fun Byte.toBitString(bitCharTrue: Char = '1', bitCharFalse: Char = '0'): String {
    var result = ""
    for (index in 0..7) {
        result += if (this[index]) bitCharTrue else bitCharFalse
    }
    return result
}

fun Byte.toReversedBitString(bitCharTrue: Char = '1', bitCharFalse: Char = '0'): String {
    return this.toBitString(bitCharTrue, bitCharFalse).reversed()
}
