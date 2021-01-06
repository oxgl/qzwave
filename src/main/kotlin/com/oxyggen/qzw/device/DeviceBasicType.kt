package com.oxyggen.qzw.device

import com.oxyggen.qzw.function.ByteToEnum

enum class DeviceBasicType(val byteValue: Byte) {
    CONTROLLER(0x01.toByte()),
    STATIC_CONTROLLER(0x02.toByte()),
    SLAVE(0x03.toByte()),
    ROUTING_SLAVE(0x04.toByte());

    companion object : ByteToEnum<DeviceBasicType>{
        override fun getByByteValue(byteValue: Byte): DeviceBasicType? =
            values().firstOrNull { it.byteValue == byteValue }
    }
}