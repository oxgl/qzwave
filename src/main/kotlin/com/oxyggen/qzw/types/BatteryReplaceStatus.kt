package com.oxyggen.qzw.types

enum class BatteryReplaceStatus(override val byteValue: Byte) : TypeToByte {
    HEALTHY(0x00.toByte()),
    REPLACE_SOON(0x01.toByte()),
    REPLACE_NOW(0x03.toByte());

    companion object : ByteToType<BatteryReplaceStatus> {
        override fun getByByteValue(byteValue: Byte): BatteryReplaceStatus = when (byteValue) {
            HEALTHY.byteValue -> HEALTHY
            REPLACE_SOON.byteValue -> REPLACE_SOON
            else -> REPLACE_NOW
        }
    }
}