package com.oxyggen.qzw.device

enum class DeviceGenericType(val byteValue: Byte) {
    APPLIANCE(0x06.toByte()),
    AV_CONTROL_POINT(0x03.toByte()),
    DISPLAY(0x04.toByte()),
    ENTRY_CONTROL(0x40.toByte()),
    GENERIC_CONTROLLER(0x01.toByte()),
    METER(0x31.toByte()),
    METER_PULSE(0x30.toByte()),
    NETWORK_EXTENDER(0x05.toByte()),
    NON_INTEROPERABLE(0xFF.toByte()),
    REPEATER_SLAVE(0x0F.toByte()),
    SECURITY_PANEL(0x17.toByte()),
    SEMI_INTEROPERABLE(0x50.toByte()),
    SENSOR_ALARM(0xA1.toByte()),
    SENSOR_BINARY(0x20.toByte()),
    SENSOR_MULTILEVEL(0x21.toByte()),
    SENSOR_NOTIFICATION(0x07.toByte()),
    STATIC_CONTROLLER(0x02.toByte()),
    SWITCH_BINARY(0x10.toByte()),
    SWITCH_MULTILEVEL(0x11.toByte()),
    SWITCH_REMOTE(0x12.toByte()),
    SWITCH_TOGGLE(0x13.toByte()),
    THERMOSTAT(0x08.toByte()),
    VENTILATION(0x16.toByte()),
    WALL_CONTROLLER(0x18.toByte()),
    WINDOW_COVERING(0x09.toByte()),
    ZIP_NODE(0x15.toByte());

    companion object {
        fun getByByteValue(byteValue: Byte): DeviceGenericType? =
            values().firstOrNull { it.byteValue == byteValue }
    }
}