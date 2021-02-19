package com.oxyggen.qzw.types

enum class DeviceBasicType(override val byteValue: Byte) : TypeToByte {
    CONTROLLER(0x01.toByte()),
    STATIC_CONTROLLER(0x02.toByte()),
    SLAVE(0x03.toByte()),
    ROUTING_SLAVE(0x04.toByte());

    companion object : ByteToType<DeviceBasicType> {
        override fun getByByteValue(byteValue: Byte): DeviceBasicType? =
            values().firstOrNull { it.byteValue == byteValue }
    }
}