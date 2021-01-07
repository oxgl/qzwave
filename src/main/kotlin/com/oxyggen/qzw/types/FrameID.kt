package com.oxyggen.qzw.types

enum class FrameID(val byteValue: Byte) {
    SOF(0x01.toByte()),
    ACK(0x06.toByte()),
    NAK(0x15.toByte()),
    CAN(0x18.toByte());

    companion object : ByteToClass<FrameID> {
        override fun getByByteValue(byteValue: Byte): FrameID? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}