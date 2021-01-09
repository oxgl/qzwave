package com.oxyggen.qzw.types

enum class FrameID(override val byteValue: Byte) : TypeToByte {
    SOF(0x01.toByte()),
    ACK(0x06.toByte()),
    NAK(0x15.toByte()),
    CAN(0x18.toByte());

    companion object : ByteToType<FrameID> {
        override fun getByByteValue(byteValue: Byte): FrameID? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}