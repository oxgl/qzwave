package com.oxyggen.qzw.device

enum class DeviceBasicType(val byteValue: Byte) {
    CONTROLLER(0x01.toByte()),
    STATIC_CONTROLLER(0x02.toByte()),
    SLAVE(0x03.toByte()),
    ROUTING_SLAVE(0x04.toByte());

    companion object {
        fun getByByteValue(byteValue: Byte): DeviceBasicType? =
            DeviceBasicType.values().firstOrNull { it.byteValue == byteValue }
    }
}