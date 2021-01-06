package com.oxyggen.qzw.types

import com.oxyggen.qzw.function.ByteToEnum

enum class FrameID(val byteValue: Byte) {
    SOF(0x01.toByte()),
    ACK(0x06.toByte()),
    NAK(0x15.toByte()),
    CAN(0x18.toByte());

    companion object : ByteToEnum<FrameID> {
        override fun getByByteValue(byteValue: Byte): FrameID? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}