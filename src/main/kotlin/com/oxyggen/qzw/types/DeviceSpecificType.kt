package com.oxyggen.qzw.types

enum class DeviceSpecificType(val genericType: DeviceGenericType?, val byteValue: Byte) {

    // Common value: not used for all types
    NOT_USED(null, 0x00),

    // Device class Av Control Point
    DOORBELL(DeviceGenericType.AV_CONTROL_POINT, 0x12),
    SATELLITE_RECEIVER(DeviceGenericType.AV_CONTROL_POINT, 0x04),
    SATELLITE_RECEIVER_V2(DeviceGenericType.AV_CONTROL_POINT, 0x11),

    // Device class Display
    SIMPLE_DISPLAY(DeviceGenericType.DISPLAY, 0x01),

    // Device class Entry Control
    DOOR_LOCK(DeviceGenericType.ENTRY_CONTROL, 0x01),
    ADVANCED_DOOR_LOCK(DeviceGenericType.ENTRY_CONTROL, 0x02),
    SECURE_KEYPAD_DOOR_LOCK(DeviceGenericType.ENTRY_CONTROL, 0x03),
    SECURE_KEYPAD_DOOR_LOCK_DEADBOLT(DeviceGenericType.ENTRY_CONTROL, 0x04),
    SECURE_DOOR(DeviceGenericType.ENTRY_CONTROL, 0x05),
    SECURE_GATE(DeviceGenericType.ENTRY_CONTROL, 0x06),
    SECURE_BARRIER_ADDON(DeviceGenericType.ENTRY_CONTROL, 0x07),
    SECURE_BARRIER_OPEN_ONLY(DeviceGenericType.ENTRY_CONTROL, 0x08),
    SECURE_BARRIER_CLOSE_ONLY(DeviceGenericType.ENTRY_CONTROL, 0x09),
    SECURE_LOCKBOX(DeviceGenericType.ENTRY_CONTROL, 0x0A),
    SECURE_KEYPAD(DeviceGenericType.ENTRY_CONTROL, 0x0B),

    // Device class Generic Controller
    PORTABLE_REMOTE_CONTROLLER(DeviceGenericType.GENERIC_CONTROLLER, 0x01),
    PORTABLE_SCENE_CONTROLLER(DeviceGenericType.GENERIC_CONTROLLER, 0x02),
    PORTABLE_INSTALLER_TOOL(DeviceGenericType.GENERIC_CONTROLLER, 0x03),
    REMOTE_CONTROL_AV(DeviceGenericType.GENERIC_CONTROLLER, 0x04),
    REMOTE_CONTROL_SIMPLE(DeviceGenericType.GENERIC_CONTROLLER, 0x06),

    // Device class Switch Binary
    POWER_SWITCH_BINARY(DeviceGenericType.SWITCH_BINARY, 0x01),
    SCENE_SWITCH_BINARY(DeviceGenericType.SWITCH_BINARY, 0x03),
    POWER_STRIP(DeviceGenericType.SWITCH_BINARY, 0x04),
    SIREN(DeviceGenericType.SWITCH_BINARY, 0x05),
    VALVE_OPEN_CLOSE(DeviceGenericType.SWITCH_BINARY, 0x06),
    COLOR_TUNABLE_BINARY(DeviceGenericType.SWITCH_BINARY, 0x02),
    IRRIGATION_CONTROLLER(DeviceGenericType.SWITCH_BINARY, 0x07),

    // Device class Switch Multilevel
    CLASS_A_MOTOR_CONTROL(DeviceGenericType.SWITCH_MULTILEVEL, 0x05),
    CLASS_B_MOTOR_CONTROL(DeviceGenericType.SWITCH_MULTILEVEL, 0x06),
    CLASS_C_MOTOR_CONTROL(DeviceGenericType.SWITCH_MULTILEVEL, 0x07),
    MOTOR_MULTIPOSITION(DeviceGenericType.SWITCH_MULTILEVEL, 0x03),
    POWER_SWITCH_MULTILEVEL(DeviceGenericType.SWITCH_MULTILEVEL, 0x01),
    SCENE_SWITCH_MULTILEVEL(DeviceGenericType.SWITCH_MULTILEVEL, 0x04),
    FAN_SWITCH(DeviceGenericType.SWITCH_MULTILEVEL, 0x08),
    COLOR_TUNABLE_MULTILEVEL(DeviceGenericType.SWITCH_MULTILEVEL, 0x02),

    // Device class Switch Remote
    SWITCH_REMOTE_BINARY(DeviceGenericType.SWITCH_REMOTE, 0x01),
    SWITCH_REMOTE_MULTILEVEL(DeviceGenericType.SWITCH_REMOTE, 0x02),
    SWITCH_REMOTE_TOGGLE_BINARY(DeviceGenericType.SWITCH_REMOTE, 0x03),
    SWITCH_REMOTE_TOGGLE_MULTILEVEL(DeviceGenericType.SWITCH_REMOTE, 0x04),

    // Device class Switch Toggle
    SWITCH_TOGGLE_BINARY(DeviceGenericType.SWITCH_TOGGLE, 0x01),
    SWITCH_TOGGLE_MULTILEVEL(DeviceGenericType.SWITCH_TOGGLE, 0x02),

    // Device class Thermostat
    SETBACK_SCHEDULE_THERMOSTAT(DeviceGenericType.THERMOSTAT, 0x03),
    SETBACK_THERMOSTAT(DeviceGenericType.THERMOSTAT, 0x05),
    SETPOINT_THERMOSTAT(DeviceGenericType.THERMOSTAT, 0x04),
    THERMOSTAT_GENERAL(DeviceGenericType.THERMOSTAT, 0x02),
    THERMOSTAT_GENERAL_V2(DeviceGenericType.THERMOSTAT, 0x06),
    THERMOSTAT_HEATING(DeviceGenericType.THERMOSTAT, 0x01),

    // Device class Ventilation
    RESIDENTIAL_HRV(DeviceGenericType.VENTILATION, 0x01),

    // Device class Window Covering
    SIMPLE_WINDOW_COVERING(DeviceGenericType.WINDOW_COVERING, 0x01),

    // Device class Zip Node
    ZIP_ADV_NODE(DeviceGenericType.ZIP_NODE, 0x02),
    ZIP_TUN_NODE(DeviceGenericType.ZIP_NODE, 0x01),

    // Device class Wall Controller
    BASIC_WALL_CONTROLLER(DeviceGenericType.WALL_CONTROLLER, 0x01),

    // Device class Network Extender
    SECURE_EXTENDER(DeviceGenericType.NETWORK_EXTENDER, 0x01),

    // Device class Appliance
    GENERAL_APPLIANCE(DeviceGenericType.APPLIANCE, 0x01),
    KITCHEN_APPLIANCE(DeviceGenericType.APPLIANCE, 0x02),
    LAUNDRY_APPLIANCE(DeviceGenericType.APPLIANCE, 0x03),

    // Device class Sensor Notification
    NOTIFICATION_SENSOR(DeviceGenericType.SENSOR_NOTIFICATION, 0x01);


    companion object {
        fun getByByteValue(genericType: DeviceGenericType, byteValue: Byte): DeviceSpecificType? =
            if (byteValue == NOT_USED.byteValue)
                NOT_USED
            else
                values().firstOrNull { it.genericType == genericType && it.byteValue == byteValue }
    }
}
