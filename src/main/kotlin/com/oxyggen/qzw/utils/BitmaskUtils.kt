package com.oxyggen.qzw.utils

import com.oxyggen.qzw.extensions.get
import com.oxyggen.qzw.extensions.withBit

@ExperimentalUnsignedTypes
class BitmaskUtils {
    companion object {
        fun decompressBitmask(byteArray: ByteArray): List<Boolean> {
            val result = mutableListOf<Boolean>()
            byteArray.forEach {
                for (bitIndex in 0..7)
                    result.add(it[bitIndex])
            }
            return result
        }

        fun decompressBitmaskToByteSet(byteArray: ByteArray, startIndex: Byte = 1): Set<Byte> {
            val result = mutableSetOf<Byte>()
            var index = startIndex
            byteArray.forEach {
                for (bitIndex in 0..7) {
                    if (it[bitIndex]) result += index
                    index++
                }
            }
            return result
        }

        fun decompressBitmaskToUByteSet(byteArray: ByteArray, startIndex: UByte = 1u): Set<UByte> {
            val result = mutableSetOf<UByte>()
            var index = startIndex
            byteArray.forEach {
                for (bitIndex in 0..7) {
                    if (it[bitIndex]) result += index
                    index++
                }
            }
            return result
        }

        fun compressUByteSetToBitmask(input: Set<UByte>, resultBytes: Int? = null, startIndex: UByte = 1u): ByteArray {

            val maxKey = input.maxOrNull()?.toInt() ?: 0

            // Number of bits + 7 div 8
            val requiredBytes = (maxKey - startIndex.toInt() + 7).div(8)

            // Calculate result size
            val resultSize = resultBytes ?: requiredBytes

            val result = ByteArray(resultSize)
            input.forEach {
                val byteIndex = (it.toInt() - startIndex.toInt()).div(8)
                val bitIndex = (it.toInt() - startIndex.toInt()).rem(8)

                if (byteIndex < resultSize)
                    result[byteIndex] = result[byteIndex].withBit(bitIndex)
            }
            return result
        }
    }

}