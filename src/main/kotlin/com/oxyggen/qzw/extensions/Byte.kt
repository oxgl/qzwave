package com.oxyggen.qzw.extensions

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

operator fun Byte.get(index: Int): Boolean = this.and(1.shl(index).toByte()) == 1.shl(index).toByte()

fun Byte.Companion.build(vararg bits: Boolean): Byte {       // Bits from bit 0 -> bit n
    var result = 0x00.toByte()
    for ((index, b) in bits.withIndex()) {
        result = result.withBit(index, b)
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
