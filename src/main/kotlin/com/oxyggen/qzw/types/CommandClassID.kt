package com.oxyggen.qzw.types

import com.oxyggen.qzw.function.ByteToEnum

enum class CommandClassID(val byteValue: Byte, val maxVersion: Int = 1, val category: Category) {

    // Alarm Command Class
    ALARM(0x71.toByte(), 2, Category.APPLICATION),

    // Alarm Sensor Command Class
    SENSOR_ALARM(0x9C.toByte(), 1, Category.APPLICATION),

    // Alarm Silence Command Class
    SILENCE_ALARM(0x9D.toByte(), 1, Category.APPLICATION),

    // All Switch Command Class
    SWITCH_ALL(0x27.toByte(), 1, Category.APPLICATION),

    // Anti-theft Command Class
    ANTITHEFT(0x5D.toByte(), 3, Category.APPLICATION),

    // Anti-theft Unlock Command Class
    ANTITHEFT_UNLOCK(0x7E.toByte(), 1, Category.APPLICATION),

    // Application Capability Command Class
    APPLICATION_CAPABILITY(0x57.toByte(), 1, Category.MANAGEMENT),

    // Application Status Command Class
    APPLICATION_STATUS(0x22.toByte(), 1, Category.MANAGEMENT),

    // Association Command Class
    ASSOCIATION(0x85.toByte(), 3, Category.MANAGEMENT),

    // Association Command Configuration Command Class
    ASSOCIATION_COMMAND_CONFIGURATION(0x9B.toByte(), 1, Category.MANAGEMENT),

    // Association Group Information (AGI) Command Class
    ASSOCIATION_GRP_INFO(0x59.toByte(), 3, Category.MANAGEMENT),

    // Authentication Command Class
    AUTHENTICATION(0xA1.toByte(), 1, Category.APPLICATION),

    // Authentication Media Write Command Class
    AUTHENTICATION_MEDIA_WRITE(0xA2.toByte(), 1, Category.APPLICATION),

    // Barrier Operator Command Class
    BARRIER_OPERATOR(0x66.toByte(), 1, Category.APPLICATION),

    // Basic Command Class
    BASIC(0x20.toByte(), 2, Category.APPLICATION),

    // Basic Tariff Information Command Class
    BASIC_TARIFF_INFO(0x36.toByte(), 1, Category.APPLICATION),

    // Basic Window Covering Command Class
    BASIC_WINDOW_COVERING(0x50.toByte(), 1, Category.APPLICATION),

    // Battery Command Class
    BATTERY(0x80.toByte(), 3, Category.MANAGEMENT),

    // Binary Sensor Command Class
    SENSOR_BINARY(0x30.toByte(), 2, Category.APPLICATION),

    // Binary Switch Command Class
    SWITCH_BINARY(0x25.toByte(), 2, Category.APPLICATION),

    // Binary Toggle Switch Command Class
    SWITCH_TOGGLE_BINARY(0x28.toByte(), 1, Category.APPLICATION),

    // Climate Control Schedule Command Class
    CLIMATE_CONTROL_SCHEDULE(0x46.toByte(), 1, Category.APPLICATION),

    // Central Scene Command Class
    CENTRAL_SCENE(0x5B.toByte(), 3, Category.APPLICATION),

    // Clock Command Class
    CLOCK(0x81.toByte(), 1, Category.APPLICATION),

    // Color Switch Command Class
    SWITCH_COLOR(0x33.toByte(), 3, Category.APPLICATION),

    // Configuration Command Class
    CONFIGURATION(0x70.toByte(), 4, Category.APPLICATION),

    // Controller Replication Command Class
    CONTROLLER_REPLICATION(0x21.toByte(), 1, Category.APPLICATION),

    // CRC-16 Encapsulation Command Class
    CRC_16_ENCAP(0x56.toByte(), 1, Category.TRANSPORT_ENCAPSULATION),

    // Demand Control Plan Configuration Command Class
    DCP_CONFIG(0x3A.toByte(), 1, Category.APPLICATION),

    // Demand Control Plan Monitor Command Class
    DCP_MONITOR(0x3B.toByte(), 1, Category.APPLICATION),

    // Device Reset Locally Command Class
    DEVICE_RESET_LOCALLY(0x5A.toByte(), 1, Category.MANAGEMENT),

    // Door Lock Command Class
    DOOR_LOCK(0x62.toByte(), 4, Category.APPLICATION),

    // Door Lock Logging Command Class
    DOOR_LOCK_LOGGING(0x4C.toByte(), 1, Category.APPLICATION),

    // Energy Production Command Class
    ENERGY_PRODUCTION(0x90.toByte(), 1, Category.APPLICATION),

    // Entry Control Command Class
    ENTRY_CONTROL(0x6F.toByte(), 1, Category.APPLICATION),

    // Firmware Update Meta Data Command Class
    FIRMWARE_UPDATE_MD(0x7A.toByte(), 7, Category.MANAGEMENT),

    // Generic Schedule Command Class
    GENERIC_SCHEDULE(0xA3.toByte(), 1, Category.APPLICATION),

    // Geographic Location Command Class
    GEOGRAPHIC_LOCATION(0x8C.toByte(), 1, Category.APPLICATION),

    // Grouping Name Command Class
    GROUPING_NAME(0x7B.toByte(), 1, Category.MANAGEMENT),

    // Hail Command Class
    HAIL(0x82.toByte(), 1, Category.MANAGEMENT),

    // HRV Status Command Class
    HRV_STATUS(0x37.toByte(), 1, Category.APPLICATION),

    // HRV Control Command Class
    HRV_CONTROL(0x39.toByte(), 1, Category.APPLICATION),

    // Humidity Control Mode Command Class
    HUMIDITY_CONTROL_MODE(0x6D.toByte(), 2, Category.APPLICATION),

    // Humidity Control Operating State Command Class
    HUMIDITY_CONTROL_OPERATING_STATE(0x6E.toByte(), 1, Category.APPLICATION),

    // Humidity Control Setpoint Command Class
    HUMIDITY_CONTROL_SETPOINT(0x64.toByte(), 2, Category.APPLICATION),

    // Inclusion Controller Command Class
    INCLUSION_CONTROLLER(0x74.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Indicator Command Class
    INDICATOR(0x87.toByte(), 4, Category.MANAGEMENT),

    // IP Association Command Class
    IP_ASSOCIATION(0x5C.toByte(), 1, Category.MANAGEMENT),

    // IP Configuration Command Class
    IP_CONFIGURATION(0x9A.toByte(), 1, Category.MANAGEMENT),

    // IR Repeater Command Class
    IR_REPEATER(0xA0.toByte(), 1, Category.APPLICATION),

    // Irrigation Command Class
    IRRIGATION(0x6B.toByte(), 1, Category.APPLICATION),

    // Language Command Class
    LANGUAGE(0x89.toByte(), 1, Category.APPLICATION),

    // Lock Command Class
    LOCK(0x76.toByte(), 1, Category.APPLICATION),

    // Mailbox Command Class
    MAILBOX(0x69.toByte(), 2, Category.NETWORK_PROTOCOL),

    // Manufacturer proprietary Command Class
    MANUFACTURER_PROPRIETARY(0x91.toByte(), 1, Category.APPLICATION),

    // Manufacturer Specific Command Class
    MANUFACTURER_SPECIFIC(0x72.toByte(), 2, Category.MANAGEMENT),

    // Meter Command Class
    METER(0x32.toByte(), 6, Category.APPLICATION),

    // Meter Table Configuration Command Class
    METER_TBL_CONFIG(0x3C.toByte(), 1, Category.APPLICATION),

    // Meter Table Monitor Command Class
    METER_TBL_MONITOR(0x3D.toByte(), 3, Category.APPLICATION),

    // Meter Table Push Configuration Command Class
    METER_TBL_PUSH(0x3E.toByte(), 1, Category.APPLICATION),

    // Move To Position Window Covering Command Class
    MTP_WINDOW_COVERING(0x51.toByte(), 1, Category.APPLICATION),

    // Multi Channel Command Class
    MULTI_CHANNEL(0x60.toByte(), 4, Category.TRANSPORT_ENCAPSULATION),

    // Multi Channel Association Command Class
    MULTI_CHANNEL_ASSOCIATION(0x8E.toByte(), 4, Category.MANAGEMENT),

    // Multi Command Command Class
    MULTI_CMD(0x8F.toByte(), 1, Category.TRANSPORT_ENCAPSULATION),

    // Multilevel Sensor Command Class
    SENSOR_MULTILEVEL(0x31.toByte(), 11, Category.APPLICATION),

    // Multilevel Switch Command Class
    SWITCH_MULTILEVEL(0x26.toByte(), 4, Category.APPLICATION),

    // Multilevel Toggle Switch Command Class
    SWITCH_TOGGLE_MULTILEVEL(0x29.toByte(), 1, Category.APPLICATION),

    // Network Management Basic Node Command Class
    NETWORK_MANAGEMENT_BASIC(0x4D.toByte(), 2, Category.NETWORK_PROTOCOL),

    // Network Management Inclusion Command Class
    NETWORK_MANAGEMENT_INCLUSION(0x34.toByte(), 3, Category.NETWORK_PROTOCOL),

    // Network Management Installation and Maintenance Command Class
    NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE(0x67.toByte(), 2, Category.NETWORK_PROTOCOL),

    // Network Management Primary Command Class
    NETWORK_MANAGEMENT_PRIMARY(0x54.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Network Management Proxy Command Class
    NETWORK_MANAGEMENT_PROXY(0x52.toByte(), 3, Category.NETWORK_PROTOCOL),

    // No Operation Command Class
    NO_OPERATION(0x00.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Node Naming and Location Command Class
    NODE_NAMING(0x77.toByte(), 1, Category.MANAGEMENT),

    // Node Provisioning Command Class
    NODE_PROVISIONING(0x78.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Notification Command Class
    NOTIFICATION(0x71.toByte(), 8, Category.APPLICATION),

    // Powerlevel Command Class
    POWERLEVEL(0x73.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Prepayment Command Class
    PREPAYMENT(0x3F.toByte(), 1, Category.APPLICATION),

    // Prepayment Encapsulation Command Class
    PREPAYMENT_ENCAPSULATION(0x41.toByte(), 1, Category.APPLICATION),

    // Proprietary Command Class
    PROPRIETARY(0x88.toByte(), 1, Category.APPLICATION),

    // Protection Command Class
    PROTECTION(0x75.toByte(), 2, Category.APPLICATION),

    // Pulse Meter Command Class
    METER_PULSE(0x35.toByte(), 1, Category.APPLICATION),

    // Rate Table Configuration Command Class
    RATE_TBL_CONFIG(0x48.toByte(), 1, Category.APPLICATION),

    // Rate Table Monitor Command Class
    RATE_TBL_MONITOR(0x49.toByte(), 1, Category.APPLICATION),

    // Remote Association Activation Command Class
    REMOTE_ASSOCIATION_ACTIVATE(0x7C.toByte(), 1, Category.MANAGEMENT),

    // Remote Association Configuration Command Class
    REMOTE_ASSOCIATION(0x7D.toByte(), 1, Category.MANAGEMENT),

    // Scene Activation Command Class
    SCENE_ACTIVATION(0x2B.toByte(), 1, Category.APPLICATION),

    // Scene Actuator Configuration Command Class
    SCENE_ACTUATOR_CONF(0x2C.toByte(), 1, Category.APPLICATION),

    // Scene Controller Configuration Command Class
    SCENE_CONTROLLER_CONF(0x2D.toByte(), 1, Category.APPLICATION),

    // Schedule Command Class
    SCHEDULE(0x53.toByte(), 4, Category.APPLICATION),

    // Schedule Entry Lock Command Class
    SCHEDULE_ENTRY_LOCK(0x4E.toByte(), 3, Category.APPLICATION),

    // Screen Attributes Command Class
    SCREEN_ATTRIBUTES(0x93.toByte(), 2, Category.APPLICATION),

    // Screen Meta Data Command Class
    SCREEN_MD(0x92.toByte(), 2, Category.APPLICATION),

    // Security 0 Command Class
    SECURITY(0x98.toByte(), 1, Category.TRANSPORT_ENCAPSULATION),

    // Security 2 Command Class
    SECURITY_2(0x9F.toByte(), 1, Category.TRANSPORT_ENCAPSULATION),

    // Sensor Configuration Command Class
    SENSOR_CONFIGURATION(0x9E.toByte(), 1, Category.APPLICATION),

    // Simple AV Control Command Class
    SIMPLE_AV_CONTROL(0x94.toByte(), 4, Category.APPLICATION),

    // Sound Switch Command Class
    SOUND_SWITCH(0x79.toByte(), 2, Category.APPLICATION),

    // Supervision Command Class
    SUPERVISION(0x6C.toByte(), 2, Category.TRANSPORT_ENCAPSULATION),

    // Tariff Table Configuration Command Class
    TARIFF_CONFIG(0x4A.toByte(), 1, Category.APPLICATION),

    // Tariff Table Monitor Command Class
    TARIFF_TBL_MONITOR(0x4B.toByte(), 1, Category.APPLICATION),

    // Thermostat Fan Mode Command Class
    THERMOSTAT_FAN_MODE(0x44.toByte(), 5, Category.APPLICATION),

    // Thermostat Fan State Command Class
    THERMOSTAT_FAN_STATE(0x45.toByte(), 2, Category.APPLICATION),

    // Thermostat Mode Command Class
    THERMOSTAT_MODE(0x40.toByte(), 3, Category.APPLICATION),

    // Thermostat Operating State Command Class
    THERMOSTAT_OPERATING_STATE(0x42.toByte(), 2, Category.APPLICATION),

    // Thermostat Setback Command Class
    THERMOSTAT_SETBACK(0x47.toByte(), 1, Category.APPLICATION),

    // Thermostat Setpoint Command Class
    THERMOSTAT_SETPOINT(0x43.toByte(), 3, Category.APPLICATION),

    // Time Command Class
    TIME(0x8A.toByte(), 2, Category.MANAGEMENT),

    // Time Parameters Command Class
    TIME_PARAMETERS(0x8B.toByte(), 2, Category.MANAGEMENT),

    // Transport Service Command Class
    TRANSPORT_SERVICE(0x55.toByte(), 2, Category.TRANSPORT_ENCAPSULATION),

    // User Code Command Class
    USER_CODE(0x63.toByte(), 2, Category.APPLICATION),

    // Version Command Class
    VERSION(0x86.toByte(), 3, Category.MANAGEMENT),

    // Wake Up Command Class
    WAKE_UP(0x84.toByte(), 3, Category.MANAGEMENT),

    // Window Covering Command Class
    WINDOW_COVERING(0x6A.toByte(), 1, Category.APPLICATION),

    // Z/IP Command Class
    ZIP(0x23.toByte(), 4, Category.NETWORK_PROTOCOL),

    // Z/IP 6LoWPAN Command Class
    ZIP_6LOWPAN(0x4F.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Z/IP Gateway Command Class
    ZIP_GATEWAY(0x5F.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Z/IP Naming and Location Command Class
    ZIP_NAMING(0x68.toByte(), 1, Category.MANAGEMENT),

    // Z/IP ND Command Class
    ZIP_ND(0x58.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Z/IP Portal Command Class
    ZIP_PORTAL(0x61.toByte(), 1, Category.NETWORK_PROTOCOL),

    // Z-Wave Plus Info Command Class
    ZWAVEPLUS_INFO(0x5E.toByte(), 2, Category.MANAGEMENT);

    enum class Category {
        APPLICATION,
        MANAGEMENT,
        TRANSPORT_ENCAPSULATION,
        NETWORK_PROTOCOL
    }


    companion object : ByteToEnum<CommandClassID> {
        private val byteValueToCC: Map<Byte, List<CommandClassID>> by lazy {
            val result = mutableMapOf<Byte, List<CommandClassID>>()
            values().forEach {
                val ccs =
                    result.getOrPut(it.byteValue, { mutableListOf() }) as MutableList<CommandClassID>
                ccs.add(it)
            }
            result.forEach { entry ->
                if (entry.value.size > 1) (entry.value as MutableList<CommandClassID>).sortBy { -it.maxVersion }
            }
            result
        }

        fun getByByteValueVer(byteValue: Byte, version: Int = -1): CommandClassID? =
            if (version == -1)
                byteValueToCC[byteValue]?.get(0)
            else
                byteValueToCC[byteValue]?.findLast { it.maxVersion >= version }

        override fun getByByteValue(byteValue: Byte): CommandClassID? = getByByteValueVer(byteValue)
    }

}