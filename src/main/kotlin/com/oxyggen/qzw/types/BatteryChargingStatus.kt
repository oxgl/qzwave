package com.oxyggen.qzw.types

enum class BatteryChargingStatus(override val byteValue: Byte) : TypeToByte {
    DISCHARGING(0x00.toByte()),
    CHARGING(0x01.toByte()),
    MAINTAINING(0x02.toByte());

    companion object : ByteToType<BatteryChargingStatus> {
        override fun getByByteValue(byteValue: Byte): BatteryChargingStatus? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}