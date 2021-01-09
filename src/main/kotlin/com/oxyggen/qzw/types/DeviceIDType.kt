package com.oxyggen.qzw.types

enum class DeviceIDType(override val byteValue: Byte) : TypeToByte {
    FACTORY_DEFAULT(0x00.toByte()),
    SERIAL_NUMBER(0x01.toByte()),
    PSEUDO_RANDOM(0x02.toByte());

    companion object : ByteToType<DeviceIDType> {
        override fun getByByteValue(byteValue: Byte): DeviceIDType? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}