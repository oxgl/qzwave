package com.oxyggen.qzw.types

enum class FrameType(override val byteValue: Byte) : TypeToByte {
    REQUEST(0x00),
    RESPONSE(0x01);

    companion object : ByteToType<FrameType> {
        override fun getByByteValue(byteValue: Byte): FrameType? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}