package com.oxyggen.qzw.types

import com.oxyggen.qzw.function.ByteToEnum

enum class FrameType(val byteValue: Byte) {
    REQUEST(0x00),
    RESPONSE(0x01);

    companion object :ByteToEnum<FrameType> {
        override fun getByByteValue(byteValue: Byte): FrameType? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}