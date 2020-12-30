package com.oxyggen.qzw.types

enum class FrameType(val byteValue: Byte) {
    REQUEST(0x00),
    RESPONSE(0x01);

    companion object {
        fun getByByteValue(byteValue: Byte): FrameType? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}