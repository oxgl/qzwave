package com.oxyggen.qzw.types

enum class CommandID(val commandClass: CommandClassID, val byteValue: Byte) {
    // Alarm Get:controlling -> supporting
    ALARM_GET(CommandClassID.ALARM, 0x04.toByte()),

    // Alarm Report:supporting -> controlling
    ALARM_REPORT(CommandClassID.ALARM, 0x05.toByte()),

    // Alarm Set:controlling -> supporting
    ALARM_SET(CommandClassID.ALARM, 0x06.toByte()),

    // Alarm Type Supported Get:controlling -> supporting
    ALARM_TYPE_SUPPORTED_GET(CommandClassID.ALARM, 0x07.toByte()),

    // Alarm Type Supported Report:supporting -> controlling
    ALARM_TYPE_SUPPORTED_REPORT(CommandClassID.ALARM, 0x08.toByte()),

    // Alarm Sensor Get:controlling -> supporting
    SENSOR_ALARM_GET(CommandClassID.SENSOR_ALARM, 0x01.toByte()),

    // Alarm Sensor Report:supporting -> controlling
    SENSOR_ALARM_REPORT(CommandClassID.SENSOR_ALARM, 0x02.toByte()),

    // Alarm Sensor Supported Get:controlling -> supporting
    SENSOR_ALARM_SUPPORTED_GET(CommandClassID.SENSOR_ALARM, 0x03.toByte()),

    // Alarm Sensor Supported Report:supporting -> controlling
    SENSOR_ALARM_SUPPORTED_REPORT(CommandClassID.SENSOR_ALARM, 0x04.toByte()),

    // Alarm Silence Set:controlling -> supporting
    SENSOR_ALARM_SET(CommandClassID.SILENCE_ALARM, 0x01.toByte()),

    // All Switch Set:controlling -> supporting
    SWITCH_ALL_SET(CommandClassID.SWITCH_ALL, 0x01.toByte()),

    // All Switch Get:controlling -> supporting
    SWITCH_ALL_GET(CommandClassID.SWITCH_ALL, 0x02.toByte()),

    // All Switch Report:supporting -> controlling
    SWITCH_ALL_REPORT(CommandClassID.SWITCH_ALL, 0x03.toByte()),

    // All Switch On:controlling -> supporting
    SWITCH_ALL_ON(CommandClassID.SWITCH_ALL, 0x04.toByte()),

    // All Switch Off:controlling -> supporting
    SWITCH_ALL_OFF(CommandClassID.SWITCH_ALL, 0x05.toByte()),

    // Anti-theft Set:controlling -> supporting
    ANTITHEFT_SET(CommandClassID.ANTITHEFT, 0x01.toByte()),

    // Anti-theft Get:controlling -> supporting
    ANTITHEFT_GET(CommandClassID.ANTITHEFT, 0x02.toByte()),

    // Anti-theft Report:supporting -> controlling
    ANTITHEFT_REPORT(CommandClassID.ANTITHEFT, 0x03.toByte()),

    // Anti-theft Unlock State Get:controlling -> supporting
    COMMAND_ANTITHEFT_UNLOCK_STATE_GET(CommandClassID.ANTITHEFT_UNLOCK, 0x01.toByte()),

    // Anti-theft Unlock State Report:supporting -> controlling
    COMMAND_ANTITHEFT_UNLOCK_STATE_REPORT(CommandClassID.ANTITHEFT_UNLOCK, 0x02.toByte()),

    // Anti-theft Unlock Set:controlling -> supporting
    COMMAND_ANTITHEFT_UNLOCK_SET(CommandClassID.ANTITHEFT_UNLOCK, 0x03.toByte()),

    // Not Supported Command Class:supporting -> controlling
    COMMAND_COMMAND_CLASS_NOT_SUPPORTED(CommandClassID.APPLICATION_CAPABILITY, 0x01.toByte()),

    // Application Busy:supporting -> controlling
    APPLICATION_BUSY(CommandClassID.APPLICATION_STATUS, 0x01.toByte()),

    // Application Rejected Request:supporting -> controlling
    APPLICATION_REJECTED_REQUEST(CommandClassID.APPLICATION_STATUS, 0x02.toByte()),

    // Association Set:controlling -> supporting
    ASSOCIATION_SET(CommandClassID.ASSOCIATION, 0x01.toByte()),

    // Association Get:controlling -> supporting
    ASSOCIATION_GET(CommandClassID.ASSOCIATION, 0x02.toByte()),

    // Association Report:supporting -> controlling
    ASSOCIATION_REPORT(CommandClassID.ASSOCIATION, 0x03.toByte()),

    // Association Remove:controlling -> supporting
    ASSOCIATION_REMOVE(CommandClassID.ASSOCIATION, 0x04.toByte()),

    // Association Supported Groupings Get:controlling -> supporting
    ASSOCIATION_GROUPINGS_GET(CommandClassID.ASSOCIATION, 0x05.toByte()),

    // Association Supported Groupings Report:supporting -> controlling
    ASSOCIATION_GROUPINGS_REPORT(CommandClassID.ASSOCIATION, 0x06.toByte()),

    // Association Specific Group Get:controlling -> supporting
    ASSOCIATION_SPECIFIC_GROUP_GET(CommandClassID.ASSOCIATION, 0x0B.toByte()),

    // Association Specific Group Report:supporting -> controlling
    ASSOCIATION_SPECIFIC_GROUP_REPORT(CommandClassID.ASSOCIATION, 0x0C.toByte()),

    // Command Records Supported Get:controlling -> supporting
    COMMAND_RECORDS_SUPPORTED_GET(CommandClassID.ASSOCIATION_COMMAND_CONFIGURATION, 0x01.toByte()),

    // Command Records Supported Report:supporting -> controlling
    COMMAND_RECORDS_SUPPORTED_REPORT(CommandClassID.ASSOCIATION_COMMAND_CONFIGURATION, 0x02.toByte()),

    // Command Configuration Set:controlling -> supporting
    COMMAND_CONFIGURATION_SET(CommandClassID.ASSOCIATION_COMMAND_CONFIGURATION, 0x03.toByte()),

    // Command Configuration Get:controlling -> supporting
    COMMAND_CONFIGURATION_GET(CommandClassID.ASSOCIATION_COMMAND_CONFIGURATION, 0x04.toByte()),

    // Command Configuration Report:supporting -> controlling
    COMMAND_CONFIGURATION_REPORT(CommandClassID.ASSOCIATION_COMMAND_CONFIGURATION, 0x05.toByte()),

    // Association group Name Get:controlling -> supporting
    ASSOCIATION_GROUP_NAME_GET(CommandClassID.ASSOCIATION_GRP_INFO, 0x01.toByte()),

    // Association Group Name Report:supporting -> controlling
    ASSOCIATION_GROUP_NAME_REPORT(CommandClassID.ASSOCIATION_GRP_INFO, 0x02.toByte()),

    // Association Group Info Get:controlling -> supporting
    ASSOCIATION_GROUP_INFO_GET(CommandClassID.ASSOCIATION_GRP_INFO, 0x03.toByte()),

    // Association Group Info Report:supporting -> controlling
    ASSOCIATION_GROUP_INFO_REPORT(CommandClassID.ASSOCIATION_GRP_INFO, 0x04.toByte()),

    // Association group Command List Get:controlling -> supporting
    ASSOCIATION_GROUP_COMMAND_LIST_GET(CommandClassID.ASSOCIATION_GRP_INFO, 0x05.toByte()),

    // Association Group Command List Report:supporting -> controlling
    ASSOCIATION_GROUP_COMMAND_LIST_REPORT(CommandClassID.ASSOCIATION_GRP_INFO, 0x06.toByte()),

    // Authentication Capability Get:controlling -> supporting
    AUTHENTICATION_CAPABILITIES_GET(CommandClassID.AUTHENTICATION, 0x01.toByte()),

    // Authentication Capability Report:supporting -> controlling
    AUTHENTICATION_CAPABILITIES_REPORT(CommandClassID.AUTHENTICATION, 0x02.toByte()),

    // Authentication Data Set:controlling -> supporting
    AUTHENTICATION_DATA_SET(CommandClassID.AUTHENTICATION, 0x03.toByte()),

    // Authentication Data Get:controlling -> supporting
    AUTHENTICATION_DATA_GET(CommandClassID.AUTHENTICATION, 0x04.toByte()),

    // Authentication Data Report:supporting -> controlling
    AUTHENTICATION_DATA_REPORT(CommandClassID.AUTHENTICATION, 0x05.toByte()),

    // Authentication Technologies Combination Set:controlling -> supporting
    AUTHENTICATION_TECHNOLOGIES_COMBINATION_SET(CommandClassID.AUTHENTICATION, 0x06.toByte()),

    // Authentication Technologies Combination Get:controlling -> supporting
    AUTHENTICATION_TECHNOLOGIES_COMBINATION_GET(CommandClassID.AUTHENTICATION, 0x07.toByte()),

    // Authentication Technologies Combination Report:supporting -> controlling
    AUTHENTICATION_TECHNOLOGIES_COMBINATION_REPORT(CommandClassID.AUTHENTICATION, 0x08.toByte()),

    // Authentication Checksum Get:controlling -> supporting
    AUTHENTICATION_CHECKSUM_GET(CommandClassID.AUTHENTICATION, 0x09.toByte()),

    // Authentication Checksum Report:supporting -> controlling
    AUTHENTICATION_DATA_CHECKSUM_REPORT(CommandClassID.AUTHENTICATION, 0x0F.toByte()),

    // Authentication Media Capability Get:controlling -> supporting
    AUTHENTICATION_MEDIA_CAPABILITIES_GET(CommandClassID.AUTHENTICATION_MEDIA_WRITE, 0x01.toByte()),

    // Authentication Media Capability Report:supporting -> controlling
    AUTHENTICATION_MEDIA_CAPABILITIES_REPORT(CommandClassID.AUTHENTICATION_MEDIA_WRITE, 0x02.toByte()),

    // Authentication Media Write Start:controlling -> supporting
    AUTHENTICATION_MEDIA_WRITE_START(CommandClassID.AUTHENTICATION_MEDIA_WRITE, 0x03.toByte()),

    // Authentication Media Write Stop:controlling -> supporting
    AUTHENTICATION_MEDIA_WRITE_STOP(CommandClassID.AUTHENTICATION_MEDIA_WRITE, 0x04.toByte()),

    // Authentication Media Write Status:supporting -> controlling
    AUTHENTICATION_MEDIA_WRITE_STATUS(CommandClassID.AUTHENTICATION_MEDIA_WRITE, 0x05.toByte()),

    // Barrier Operator Set:controlling -> supporting
    BARRIER_OPERATOR_SET(CommandClassID.BARRIER_OPERATOR, 0x01.toByte()),

    // Barrier Operator Get:controlling -> supporting
    BARRIER_OPERATOR_GET(CommandClassID.BARRIER_OPERATOR, 0x02.toByte()),

    // Barrier Operator Report:supporting -> controlling
    BARRIER_OPERATOR_REPORT(CommandClassID.BARRIER_OPERATOR, 0x03.toByte()),

    // Barrier Operator Get Signaling Capabilities Supported:controlling -> supporting
    BARRIER_OPERATOR_SIGNAL_SUPPORTED_GET(CommandClassID.BARRIER_OPERATOR, 0x04.toByte()),

    // Barrier Operator Report Event Signaling Capabilities Supported:supporting -> controlling
    BARRIER_OPERATOR_SIGNAL_SUPPORTED_REPORT(CommandClassID.BARRIER_OPERATOR, 0x05.toByte()),

    // Barrier Operator Event Signal Set:controlling -> supporting
    BARRIER_OPERATOR_SIGNAL_SET(CommandClassID.BARRIER_OPERATOR, 0x06.toByte()),

    // Barrier Operator Event Signaling Get:controlling -> supporting
    BARRIER_OPERATOR_SIGNAL_GET(CommandClassID.BARRIER_OPERATOR, 0x07.toByte()),

    // Barrier Operator Event Signaling Report:supporting -> controlling
    BARRIER_OPERATOR_SIGNAL_REPORT(CommandClassID.BARRIER_OPERATOR, 0x08.toByte()),

    // Basic Set:controlling -> supporting
    BASIC_SET(CommandClassID.BASIC, 0x01.toByte()),

    // Basic Get:controlling -> supporting
    BASIC_GET(CommandClassID.BASIC, 0x02.toByte()),

    // Basic Report:supporting -> controlling
    BASIC_REPORT(CommandClassID.BASIC, 0x03.toByte()),

    // Basic Tariff Information Get:controlling -> supporting
    BASIC_TARIFF_INFO_GET(CommandClassID.BASIC_TARIFF_INFO, 0x01.toByte()),

    // Basic Tariff Information Report:supporting -> controlling
    BASIC_TARIFF_INFO_REPORT(CommandClassID.BASIC_TARIFF_INFO, 0x02.toByte()),

    // Basic Window Covering Start Level Change:controlling -> supporting
    BASIC_WINDOW_COVERING_START_LEVEL_CHANGE(CommandClassID.BASIC_WINDOW_COVERING, 0x01.toByte()),

    // Basic Window covering Stop Level Change:controlling -> supporting
    BASIC_WINDOW_COVERING_STOP_LEVEL_CHANGE(CommandClassID.BASIC_WINDOW_COVERING, 0x02.toByte()),

    // Battery Get:controlling -> supporting
    BATTERY_GET(CommandClassID.BATTERY, 0x02.toByte()),

    // Battery Set:supporting -> controlling
    BATTERY_REPORT(CommandClassID.BATTERY, 0x03.toByte()),

    // Battery Health Get Command:controlling -> supporting
    BATTERY_HEALTH_GET(CommandClassID.BATTERY, 0x04.toByte()),

    // Battery Health Report Command:supporting -> controlling
    BATTERY_HEALTH_REPORT(CommandClassID.BATTERY, 0x05.toByte()),

    // Binary Sensor Get:controlling -> supporting
    SENSOR_BINARY_GET(CommandClassID.SENSOR_BINARY, 0x02.toByte()),

    // Binary Sensor Set:supporting -> controlling
    SENSOR_BINARY_REPORT(CommandClassID.SENSOR_BINARY, 0x03.toByte()),

    // Binary Sensor Get supported Sensor:controlling -> supporting
    SENSOR_BINARY_SUPPORTED_GET_SENSOR(CommandClassID.SENSOR_BINARY, 0x01.toByte()),

    // Binary Sensor Supported Sensor Report:supporting -> controlling
    SENSOR_BINARY_SUPPORTED_SENSOR_REPORT(CommandClassID.SENSOR_BINARY, 0x04.toByte()),

    // Binary Switch Set:controlling -> supporting
    SWITCH_BINARY_SET(CommandClassID.SWITCH_BINARY, 0x01.toByte()),

    // Binary Switch Get:controlling -> supporting
    SWITCH_BINARY_GET(CommandClassID.SWITCH_BINARY, 0x02.toByte()),

    // Binary Switch Report:supporting -> controlling
    SWITCH_BINARY_REPORT(CommandClassID.SWITCH_BINARY, 0x03.toByte()),

    // Binary Toggle Switch Set:controlling -> supporting
    SWITCH_TOGGLE_BINARY_SET(CommandClassID.SWITCH_TOGGLE_BINARY, 0x01.toByte()),

    // Binary Toggle Switch Get:controlling -> supporting
    SWITCH_TOGGLE_BINARY_GET(CommandClassID.SWITCH_TOGGLE_BINARY, 0x02.toByte()),

    // Binary Toggle Switch Report:supporting -> controlling
    SWITCH_TOGGLE_BINARY_REPORT(CommandClassID.SWITCH_TOGGLE_BINARY, 0x03.toByte()),

    // Schedule Set:controlling -> supporting
    CLIMATE_SCHEDULE_SET(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x01.toByte()),

    // Schedule Get:controlling -> supporting
    CLIMATE_SCHEDULE_GET(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x02.toByte()),

    // Schedule Report:supporting -> controlling
    CLIMATE_SCHEDULE_REPORT(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x03.toByte()),

    // Schedule Changed Get:controlling -> supporting
    SCHEDULE_CHANGED_GET(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x04.toByte()),

    // Schedule Changed Report:supporting -> controlling
    SCHEDULE_CHANGED_REPORT(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x05.toByte()),

    // Schedule Override Set:controlling -> supporting
    SCHEDULE_OVERRIDE_SET(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x06.toByte()),

    // Schedule Override Get:controlling -> supporting
    SCHEDULE_OVERRIDE_GET(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x07.toByte()),

    // Schedule Override Report:supporting -> controlling
    SCHEDULE_OVERRIDE_REPORT(CommandClassID.CLIMATE_CONTROL_SCHEDULE, 0x08.toByte()),

    // Central Scene Supported Get:controlling -> supporting
    CENTRAL_SCENE_SUPPORTED_GET(CommandClassID.CENTRAL_SCENE, 0x01.toByte()),

    // Central Scene Supported Report:supporting -> controlling
    CENTRAL_SCENE_SUPPORTED_REPORT(CommandClassID.CENTRAL_SCENE, 0x02.toByte()),

    // Central Scene Notification:supporting -> controlling
    CENTRAL_SCENE_NOTIFICATION(CommandClassID.CENTRAL_SCENE, 0x03.toByte()),

    // Central Scene Configuration Set:controlling -> supporting
    CENTRAL_SCENE_CONFIGURATION_SET(CommandClassID.CENTRAL_SCENE, 0x04.toByte()),

    // Central Scene Configuration Get:controlling -> supporting
    CENTRAL_SCENE_CONFIGURATION_GET(CommandClassID.CENTRAL_SCENE, 0x05.toByte()),

    // Central Scene Configuration Report:supporting -> controlling
    CENTRAL_SCENE_CONFIGURATION_REPORT(CommandClassID.CENTRAL_SCENE, 0x06.toByte()),

    // Clock Set:controlling -> supporting
    CLOCK_SET(CommandClassID.CLOCK, 0x04.toByte()),

    // Clock Get:controlling -> supporting
    CLOCK_GET(CommandClassID.CLOCK, 0x05.toByte()),

    // Clock Report:supporting -> controlling
    CLOCK_REPORT(CommandClassID.CLOCK, 0x06.toByte()),

    // Color Switch Supported Get:controlling -> supporting
    SWITCH_COLOR_SUPPORTED_GET(CommandClassID.SWITCH_COLOR, 0x01.toByte()),

    // Color Switch Supported Report:supporting -> controlling
    SWITCH_COLOR_SUPPORTED_REPORT(CommandClassID.SWITCH_COLOR, 0x02.toByte()),

    // Color Switch Get:controlling -> supporting
    SWITCH_COLOR_GET(CommandClassID.SWITCH_COLOR, 0x03.toByte()),

    // Color Switch Report:supporting -> controlling
    SWITCH_COLOR_REPORT(CommandClassID.SWITCH_COLOR, 0x04.toByte()),

    // Color Switch Set:controlling -> supporting
    SWITCH_COLOR_SET(CommandClassID.SWITCH_COLOR, 0x05.toByte()),

    // Color Switch Start Level Change:controlling -> supporting
    SWITCH_COLOR_START_LEVEL_CHANGE(CommandClassID.SWITCH_COLOR, 0x06.toByte()),

    // Color Switch Stop Level Change:controlling -> supporting
    SWITCH_COLOR_STOP_LEVEL_CHANGE(CommandClassID.SWITCH_COLOR, 0x07.toByte()),

    // Configuration Set:controlling -> supporting
    CONFIGURATION_SET(CommandClassID.CONFIGURATION, 0x04.toByte()),

    // Configuration Get:controlling -> supporting
    CONFIGURATION_GET(CommandClassID.CONFIGURATION, 0x05.toByte()),

    // Configuration Report:supporting -> controlling
    CONFIGURATION_REPORT(CommandClassID.CONFIGURATION, 0x06.toByte()),

    // Configuration Bulk Set:controlling -> supporting
    CONFIGURATION_BULK_SET(CommandClassID.CONFIGURATION, 0x07.toByte()),

    // Configuration Bulk Get:controlling -> supporting
    CONFIGURATION_BULK_GET(CommandClassID.CONFIGURATION, 0x08.toByte()),

    // Configuration Bulk Report:supporting -> controlling
    CONFIGURATION_BULK_REPORT(CommandClassID.CONFIGURATION, 0x09.toByte()),

    // Configuration Name Get:controlling -> supporting
    CONFIGURATION_NAME_GET(CommandClassID.CONFIGURATION, 0x0A.toByte()),

    // Configuration Name Report:supporting -> controlling
    CONFIGURATION_NAME_REPORT(CommandClassID.CONFIGURATION, 0x0B.toByte()),

    // Configuration Info Get:controlling -> supporting
    CONFIGURATION_INFO_GET(CommandClassID.CONFIGURATION, 0x0C.toByte()),

    // Configuration Info Report:supporting -> controlling
    CONFIGURATION_INFO_REPORT(CommandClassID.CONFIGURATION, 0x0D.toByte()),

    // Configuration Properties Get:controlling -> supporting
    CONFIGURATION_PROPERTIES_GET(CommandClassID.CONFIGURATION, 0x0E.toByte()),

    // Configuration Properties Report:supporting -> controlling
    CONFIGURATION_PROPERTIES_REPORT(CommandClassID.CONFIGURATION, 0x0F.toByte()),

    // Configuration Default Reset:controlling -> supporting
    CONFIGURATION_DEFAULT_RESET(CommandClassID.CONFIGURATION, 0x01.toByte()),

    // Transfer Group:controlling -> supporting
    CTRL_REPLICATION_TRANSFER_GROUP(CommandClassID.CONTROLLER_REPLICATION, 0x31.toByte()),

    // Transfer Group Name:controlling -> supporting
    CTRL_REPLICATION_TRANSFER_GROUP_NAME(CommandClassID.CONTROLLER_REPLICATION, 0x32.toByte()),

    // Transfer Scene:controlling -> supporting
    CTRL_REPLICATION_TRANSFER_SCENE(CommandClassID.CONTROLLER_REPLICATION, 0x33.toByte()),

    // Transfer Scene Name:controlling -> supporting
    CTRL_REPLICATION_TRANSFER_SCENE_NAME(CommandClassID.CONTROLLER_REPLICATION, 0x34.toByte()),

    // CRC-16 Encapsulated:controlling -> supporting
    CRC_16_ENCAP(CommandClassID.CRC_16_ENCAP, 0x01.toByte()),

    // DCP List Supported Get:controlling -> supporting
    DCP_LIST_SUPPORTED_GET(CommandClassID.DCP_CONFIG, 0x01.toByte()),

    // DCP List Supported report:supporting -> controlling
    DCP_LIST_SUPPORTED_REPORT(CommandClassID.DCP_CONFIG, 0x02.toByte()),

    // DCP List Set:controlling -> supporting
    DCP_LIST_SET(CommandClassID.DCP_CONFIG, 0x03.toByte()),

    // DCP List Remove:controlling -> supporting
    DCP_LIST_REMOVE(CommandClassID.DCP_CONFIG, 0x04.toByte()),

    // DCP List Get:controlling -> supporting
    DCP_LIST_GET(CommandClassID.DCP_MONITOR, 0x01.toByte()),

    // DCP List Report:supporting -> controlling
    DCP_LIST_REPORT(CommandClassID.DCP_MONITOR, 0x02.toByte()),

    // DCP Event Status Get:controlling -> supporting
    DCP_EVENT_STATUS_GET(CommandClassID.DCP_MONITOR, 0x03.toByte()),

    // DCP Event Status Report:supporting -> controlling
    DCP_EVENT_STATUS_REPORT(CommandClassID.DCP_MONITOR, 0x04.toByte()),

    // Device Reset Locally Notification:supporting -> controlling
    DEVICE_RESET_LOCALLY_NOTIFICATION(CommandClassID.DEVICE_RESET_LOCALLY, 0x01.toByte()),

    // Door Lock Operation Set:controlling -> supporting
    DOOR_LOCK_OPERATION_SET(CommandClassID.DOOR_LOCK, 0x01.toByte()),

    // Door Lock Operation Get:controlling -> supporting
    DOOR_LOCK_OPERATION_GET(CommandClassID.DOOR_LOCK, 0x02.toByte()),

    // Door Lock Operation Report:supporting -> controlling
    DOOR_LOCK_OPERATION_REPORT(CommandClassID.DOOR_LOCK, 0x03.toByte()),

    // Door Lock Configuration Set:controlling -> supporting
    DOOR_LOCK_CONFIGURATION_SET(CommandClassID.DOOR_LOCK, 0x04.toByte()),

    // Door Lock Configuration Get:controlling -> supporting
    DOOR_LOCK_CONFIGURATION_GET(CommandClassID.DOOR_LOCK, 0x05.toByte()),

    // Door Lock Configuration Report:supporting -> controlling
    DOOR_LOCK_CONFIGURATION_REPORT(CommandClassID.DOOR_LOCK, 0x06.toByte()),

    // Door Lock Capabilities Get:controlling -> supporting
    DOOR_LOCK_CAPABILITIES_GET(CommandClassID.DOOR_LOCK, 0x07.toByte()),

    // Door Lock Capabilities Report:supporting -> controlling
    DOOR_LOCK_CAPABILITIES_REPORT(CommandClassID.DOOR_LOCK, 0x08.toByte()),

    // Door Lock Logging Records Supported Get:controlling -> supporting
    DOOR_LOCK_LOGGING_RECORDS_SUPPORTED_GET(CommandClassID.DOOR_LOCK_LOGGING, 0x01.toByte()),

    // Door Lock Logging Records Supported Report:supporting -> controlling
    DOOR_LOCK_LOGGING_RECORDS_SUPPORTED_REPORT(CommandClassID.DOOR_LOCK_LOGGING, 0x02.toByte()),

    // Door Lock Logging Record Get:controlling -> supporting
    RECORD_GET(CommandClassID.DOOR_LOCK_LOGGING, 0x03.toByte()),

    // Door Lock Logging Record Report:supporting -> controlling
    RECORD_REPORT(CommandClassID.DOOR_LOCK_LOGGING, 0x04.toByte()),

    // Energy Production Get:controlling -> supporting
    ENERGY_PRODUCTION_GET(CommandClassID.ENERGY_PRODUCTION, 0x02.toByte()),

    // Energy Production Report:supporting -> controlling
    ENERGY_PRODUCTION_REPORT(CommandClassID.ENERGY_PRODUCTION, 0x03.toByte()),

    // Entry Control Notification:supporting -> controlling
    ENTRY_CONTROL_NOTIFICATION(CommandClassID.ENTRY_CONTROL, 0x01.toByte()),

    // Entry Control Key Supported Get:controlling -> supporting
    ENTRY_CONTROL_KEY_SUPPORTED_GET(CommandClassID.ENTRY_CONTROL, 0x02.toByte()),

    // Entry Control Key Supported Report:supporting -> controlling
    ENTRY_CONTROL_KEY_SUPPORTED_REPORT(CommandClassID.ENTRY_CONTROL, 0x03.toByte()),

    // Entry Control Event Supported Get:controlling -> supporting
    ENTRY_CONTROL_EVENT_SUPPORTED_GET(CommandClassID.ENTRY_CONTROL, 0x04.toByte()),

    // Entry Control Event Supported Report:supporting -> controlling
    ENTRY_CONTROL_EVENT_SUPPORTED_REPORT(CommandClassID.ENTRY_CONTROL, 0x05.toByte()),

    // Entry Control Configuration Set:controlling -> supporting
    ENTRY_CONTROL_CONFIGURATION_SET(CommandClassID.ENTRY_CONTROL, 0x06.toByte()),

    // Entry Control Configuration Get:controlling -> supporting
    ENTRY_CONTROL_CONFIGURATION_GET(CommandClassID.ENTRY_CONTROL, 0x07.toByte()),

    // Entry Control Configuration Report:supporting -> controlling
    ENTRY_CONTROL_CONFIGURATION_REPORT(CommandClassID.ENTRY_CONTROL, 0x08.toByte()),

    // Firmware Meta Data Get:controlling -> supporting
    FIRMWARE_MD_GET(CommandClassID.FIRMWARE_UPDATE_MD, 0x01.toByte()),

    // Firmware Meta Data Report:supporting -> controlling
    FIRMWARE_MD_REPORT(CommandClassID.FIRMWARE_UPDATE_MD, 0x02.toByte()),

    // Firmware Update Meta Data Request Get:controlling -> supporting
    FIRMWARE_UPDATE_MD_REQUEST_GET(CommandClassID.FIRMWARE_UPDATE_MD, 0x03.toByte()),

    // Firmware Update Meta Data Request Report:supporting -> controlling
    FIRMWARE_UPDATE_MD_REQUEST_REPORT(CommandClassID.FIRMWARE_UPDATE_MD, 0x04.toByte()),

    // Firmware Update Meta Data Get:supporting -> controlling
    FIRMWARE_UPDATE_MD_GET(CommandClassID.FIRMWARE_UPDATE_MD, 0x05.toByte()),

    // Firmware Update Meta Data Report:controlling -> supporting
    FIRMWARE_UPDATE_MD_REPORT(CommandClassID.FIRMWARE_UPDATE_MD, 0x06.toByte()),

    // Firmware Update Meta Data Status Report:supporting -> controlling
    FIRMWARE_UPDATE_MD_STATUS_REPORT(CommandClassID.FIRMWARE_UPDATE_MD, 0x07.toByte()),

    // Firmware Update Activation Set:controlling -> supporting
    FIRMWARE_UPDATE_ACTIVATION_SET(CommandClassID.FIRMWARE_UPDATE_MD, 0x08.toByte()),

    // Firmware Update Activation Report:supporting -> controlling
    FIRMWARE_UPDATE_ACTIVATION_STATUS_REPORT(CommandClassID.FIRMWARE_UPDATE_MD, 0x09.toByte()),

    // Firmware Update Meta Data Prepare Get:controlling -> supporting
    FIRMWARE_UPDATE_MD_PREPARE_GET(CommandClassID.FIRMWARE_UPDATE_MD, 0x0A.toByte()),

    // Firmware Update Meta Data Prepare Report:supporting -> controlling
    FIRMWARE_UPDATE_MD_PREPARE_REPORT(CommandClassID.FIRMWARE_UPDATE_MD, 0x0B.toByte()),

    // Generic Schedule Capabilities Get Command:controlling  -> supporting
    GENERIC_SCHEDULE_CAPABILITIES_GET(CommandClassID.GENERIC_SCHEDULE, 0x01.toByte()),

    // Generic Schedule Capabilities Report Command:supporting -> controlling
    GENERIC_SCHEDULE_CAPABILITIES_REPORT(CommandClassID.GENERIC_SCHEDULE, 0x02.toByte()),

    // Generic Schedule Time Range Set Command:controlling -> supporting
    GENERIC_SCHEDULE_TIME_RANGE_SET(CommandClassID.GENERIC_SCHEDULE, 0x03.toByte()),

    // Generic Schedule Time Range Get Command:controlling -> supporting
    GENERIC_SCHEDULE_TIME_RANGE_GET(CommandClassID.GENERIC_SCHEDULE, 0x04.toByte()),

    // Generic Schedule Time Range Report Command:supporting -> controlling
    GENERIC_SCHEDULE_TIME_RANGE_REPORT(CommandClassID.GENERIC_SCHEDULE, 0x05.toByte()),

    // Generic Schedule Set Command:controlling -> supporting
    GENERIC_SCHEDULE_SET(CommandClassID.GENERIC_SCHEDULE, 0x06.toByte()),

    // Generic Schedule Get Command:controlling -> supporting
    GENERIC_SCHEDULE_GET(CommandClassID.GENERIC_SCHEDULE, 0x07.toByte()),

    // Generic Schedule Report Command:supporting -> controlling
    GENERIC_SCHEDULE_REPORT(CommandClassID.GENERIC_SCHEDULE, 0x08.toByte()),

    // Geographic Location Set:controlling -> supporting
    GEOGRAPHIC_LOCATION_SET(CommandClassID.GEOGRAPHIC_LOCATION, 0x01.toByte()),

    // Geographic Location Get:controlling -> supporting
    GEOGRAPHIC_LOCATION_GET(CommandClassID.GEOGRAPHIC_LOCATION, 0x02.toByte()),

    // Geographic Location Report:supporting -> controlling
    GEOGRAPHIC_LOCATION_REPORT(CommandClassID.GEOGRAPHIC_LOCATION, 0x03.toByte()),

    // Grouping Name Set:controlling -> supporting
    GROUPING_NAME_SET(CommandClassID.GROUPING_NAME, 0x01.toByte()),

    // Grouping Name Get:controlling -> supporting
    GROUPING_NAME_GET(CommandClassID.GROUPING_NAME, 0x02.toByte()),

    // Grouping Name Report:supporting -> controlling
    GROUPING_NAME_REPORT(CommandClassID.GROUPING_NAME, 0x03.toByte()),

    // Hail:supporting -> controlling
    HAIL(CommandClassID.HAIL, 0x01.toByte()),

    // HRV Status Get:controlling -> supporting
    HRV_STATUS_GET(CommandClassID.HRV_STATUS, 0x01.toByte()),

    // HRV Status Report:supporting -> controlling
    HRV_STATUS_REPORT(CommandClassID.HRV_STATUS, 0x02.toByte()),

    // HRV Status Supported Get:controlling -> supporting
    HRV_STATUS_SUPPORTED_GET(CommandClassID.HRV_STATUS, 0x03.toByte()),

    // HRV Status Supported Report:supporting -> controlling
    HRV_STATUS_SUPPORTED_REPORT(CommandClassID.HRV_STATUS, 0x04.toByte()),

    // HRV Mode Set:controlling -> supporting
    HRV_CONTROL_MODE_SET(CommandClassID.HRV_CONTROL, 0x01.toByte()),

    // HRV Mode Get:controlling -> supporting
    HRV_CONTROL_MODE_GET(CommandClassID.HRV_CONTROL, 0x02.toByte()),

    // HRV Mode Report:supporting -> controlling
    HRV_CONTROL_MODE_REPORT(CommandClassID.HRV_CONTROL, 0x03.toByte()),

    // HRV Bypass Set:controlling -> supporting
    HRV_CONTROL_BYPASS_SET(CommandClassID.HRV_CONTROL, 0x04.toByte()),

    // HRV Bypass Get:controlling -> supporting
    HRV_CONTROL_BYPASS_GET(CommandClassID.HRV_CONTROL, 0x05.toByte()),

    // HRV Bypass Report:supporting -> controlling
    HRV_CONTROL_BYPASS_REPORT(CommandClassID.HRV_CONTROL, 0x06.toByte()),

    // HRV Ventilation Rate Set:controlling -> supporting
    HRV_CONTROL_VENTILATION_RATE_SET(CommandClassID.HRV_CONTROL, 0x07.toByte()),

    // HRV Ventilation Rate Get:controlling -> supporting
    HRV_CONTROL_VENTILATION_RATE_GET(CommandClassID.HRV_CONTROL, 0x08.toByte()),

    // HRV Ventilation Rate Report:supporting -> controlling
    HRV_CONTROL_VENTILATION_RATE_REPORT(CommandClassID.HRV_CONTROL, 0x09.toByte()),

    // HRV Mode Supported Get:controlling -> supporting
    HRV_CONTROL_MODE_SUPPORTED_GET(CommandClassID.HRV_CONTROL, 0x0A.toByte()),

    // HRV Mode Supported Report:supporting -> controlling
    HRV_CONTROL_MODE_SUPPORTED_REPORT(CommandClassID.HRV_CONTROL, 0x0B.toByte()),

    // Humidity Control Mode Set:controlling -> supporting
    HUMIDITY_CONTROL_MODE_SET(CommandClassID.HUMIDITY_CONTROL_MODE, 0x01.toByte()),

    // Humidity Control Mode Get:controlling -> supporting
    HUMIDITY_CONTROL_MODE_GET(CommandClassID.HUMIDITY_CONTROL_MODE, 0x02.toByte()),

    // Humidity Control Mode Report:supporting -> controlling
    HUMIDITY_CONTROL_MODE_REPORT(CommandClassID.HUMIDITY_CONTROL_MODE, 0x03.toByte()),

    // Humidity Control Mode Supported Get:controlling -> supporting
    HUMIDITY_CONTROL_MODE_SUPPORTED_GET(CommandClassID.HUMIDITY_CONTROL_MODE, 0x04.toByte()),

    // Humidity Control Mode Supported Report:supporting -> controlling
    HUMIDITY_CONTROL_MODE_SUPPORTED_REPORT(CommandClassID.HUMIDITY_CONTROL_MODE, 0x05.toByte()),

    // Humidity Control Operating State Get:controlling -> supporting
    HUMIDITY_CONTROL_OPERATING_STATE_GET(CommandClassID.HUMIDITY_CONTROL_OPERATING_STATE, 0x01.toByte()),

    // Humidity Control Operating State Report:supporting -> controlling
    HUMIDITY_CONTROL_OPERATING_STATE_REPORT(CommandClassID.HUMIDITY_CONTROL_OPERATING_STATE, 0x02.toByte()),

    // Humidity Control Setpoint Set:controlling -> supporting
    HUMIDITY_CONTROL_SETPOINT_SET(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x01.toByte()),

    // Humidity Control Setpoint Get:controlling -> supporting
    HUMIDITY_CONTROL_SETPOINT_GET(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x02.toByte()),

    // Humidity Control Setpoint Report:supporting -> controlling
    HUMIDITY_CONTROL_SETPOINT_REPORT(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x03.toByte()),

    // Humidity Control Setpoint Supported Get:controlling -> supporting
    HUMIDITY_CONTROL_SETPOINT_SUPPORTED_GET(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x04.toByte()),

    // Humidity Control Setpoint Supported Report:supporting -> controlling
    HUMIDITY_CONTROL_SETPOINT_SUPPORTED_REPORT(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x05.toByte()),

    // Humidity Control Setpoint Scale Supported Get:controlling -> supporting
    HUMIDITY_CONTROL_SETPOINT_SCALE_SUPPORTED_GET(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x06.toByte()),

    // Humidity Control Setpoint Scale Supported Report:supporting -> controlling
    HUMIDITY_CONTROL_SETPOINT_SCALE_SUPPORTED_REPORT(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x07.toByte()),

    // Humidity Control Setpoint Capabilities Get:controlling -> supporting
    HUMIDITY_CONTROL_SETPOINT_CAPABILITIES_GET(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x08.toByte()),

    // Humidity Control Setpoint Capabilities Report:supporting -> controlling
    HUMIDITY_CONTROL_SETPOINT_CAPABILITIES_REPORT(CommandClassID.HUMIDITY_CONTROL_SETPOINT, 0x09.toByte()),

    // Inclusion Controller Initiate:supporting -> supporting
    INITIATE(CommandClassID.INCLUSION_CONTROLLER, 0x01.toByte()),

    // Inclusion Controller Complete:supporting -> supporting
    COMPLETE(CommandClassID.INCLUSION_CONTROLLER, 0x02.toByte()),

    // Indicator Set:controlling -> supporting
    INDICATOR_SET(CommandClassID.INDICATOR, 0x01.toByte()),

    // Indicator Get:controlling -> supporting
    INDICATOR_GET(CommandClassID.INDICATOR, 0x02.toByte()),

    // Indicator Report:supporting -> controlling
    INDICATOR_REPORT(CommandClassID.INDICATOR, 0x03.toByte()),

    // Indicator Supported Get:controlling -> supporting
    INDICATOR_SUPPORTED_GET(CommandClassID.INDICATOR, 0x02.toByte()),

    // Indicator Supported Report:supporting -> controlling
    INDICATOR_SUPPORTED_REPORT(CommandClassID.INDICATOR, 0x03.toByte()),

    // Indicator description Get:controlling -> supporting
    INDICATOR_DESCRIPTION_GET(CommandClassID.INDICATOR, 0x04.toByte()),

    // Indicator Description Report:supporting -> controlling
    INDICATOR_DESCRIPTION_REPORT(CommandClassID.INDICATOR, 0x05.toByte()),

    // IP Configuration Set:controlling -> supporting
    IP_CONFIGURATION_SET(CommandClassID.IP_CONFIGURATION, 0x01.toByte()),

    // IP Configuration Get:controlling -> supporting
    IP_CONFIGURATION_GET(CommandClassID.IP_CONFIGURATION, 0x02.toByte()),

    // IP Configuration Report:supporting -> controlling
    IP_CONFIGURATION_REPORT(CommandClassID.IP_CONFIGURATION, 0x03.toByte()),

    // IP Configuration DHCP Release:controlling -> supporting
    IP_CONFIGURATION_RELEASE(CommandClassID.IP_CONFIGURATION, 0x04.toByte()),

    // IP Configuration DHCP Renew:controlling -> supporting
    IP_CONFIGURATION_RENEW(CommandClassID.IP_CONFIGURATION, 0x05.toByte()),

    // IR Repeater Capabilities Get:controlling -> supporting
    IR_REPEATER_CAPABILITIES_GET(CommandClassID.IR_REPEATER, 0x01.toByte()),

    // IR Repeater Capabilities Report:supporting -> controlling
    IR_REPEATER_CAPABILITIES_REPORT(CommandClassID.IR_REPEATER, 0x02.toByte()),

    // IR Repeater IR Code Learning Start:controlling -> supporting
    IR_REPEATER_IR_CODE_LEARNING_START(CommandClassID.IR_REPEATER, 0x03.toByte()),

    // IR Repeater IR Code Learning Stop:controlling -> supporting
    IR_REPEATER_IR_CODE_LEARNING_STOP(CommandClassID.IR_REPEATER, 0x04.toByte()),

    // IR Repeater IR Code Learning Status:supporting -> controlling
    IR_REPEATER_IR_CODE_LEARNING_STATUS(CommandClassID.IR_REPEATER, 0x05.toByte()),

    // IR Repeater Learnt IR Code Remove:controlling -> supporting
    IR_REPEATER_LEARNT_IR_CODE_REMOVE(CommandClassID.IR_REPEATER, 0x06.toByte()),

    // IR Repeater Learnt IR Code Get:controlling -> supporting
    IR_REPEATER_LEARNT_IR_CODE_GET(CommandClassID.IR_REPEATER, 0x07.toByte()),

    // IR Repeater Learnt IR Code Report:supporting -> controlling
    IR_REPEATER_LEARNT_IR_CODE_REPORT(CommandClassID.IR_REPEATER, 0x08.toByte()),

    // IR Repeater Learnt IR Code Readback Get:controlling -> supporting
    IR_REPEATER_LEARNT_IR_CODE_READBACK_GET(CommandClassID.IR_REPEATER, 0x09.toByte()),

    // IR Repeater Learnt IR Code Readback Report:supporting -> controlling
    IR_REPEATER_LEARNT_IR_CODE_READBACK_REPORT(CommandClassID.IR_REPEATER, 0x0A.toByte()),

    // IR Repeater Configuration Set:controlling -> supporting
    IR_REPEATER_CONFIGURATION_SET(CommandClassID.IR_REPEATER, 0x0B.toByte()),

    // IR Repeater Configuration Get:controlling -> supporting
    IR_REPEATER_CONFIGURATION_GET(CommandClassID.IR_REPEATER, 0x0C.toByte()),

    // IR Repeater Configuration Report:supporting -> controlling
    IR_REPEATER_CONFIGURATION_REPORT(CommandClassID.IR_REPEATER, 0x0D.toByte()),

    // IR Repeater Repeat Learnt Code:controlling -> supporting
    IR_REPEATER_REPEAT_LEARNT_CODE(CommandClassID.IR_REPEATER, 0x0E.toByte()),

    // IR Repeater Repeat:controlling -> supporting
    IR_REPEATER_REPEAT(CommandClassID.IR_REPEATER, 0x0F.toByte()),

    // Irrigation System Info Get:controlling -> supporting
    IRRIGATION_SYSTEM_INFO_GET(CommandClassID.IRRIGATION, 0x01.toByte()),

    // Irrigation System Info Report:supporting -> controlling
    IRRIGATION_SYSTEM_INFO_REPORT(CommandClassID.IRRIGATION, 0x02.toByte()),

    // Irrigation System Status Get:controlling -> supporting
    IRRIGATION_SYSTEM_STATUS_GET(CommandClassID.IRRIGATION, 0x03.toByte()),

    // Irrigation System Status Report:supporting -> controlling
    IRRIGATION_SYSTEM_STATUS_REPORT(CommandClassID.IRRIGATION, 0x04.toByte()),

    // Irrigation System Config Set:controlling -> supporting
    IRRIGATION_SYSTEM_CONFIG_SET(CommandClassID.IRRIGATION, 0x05.toByte()),

    // Irrigation System Config Get:controlling -> supporting
    IRRIGATION_SYSTEM_CONFIG_GET(CommandClassID.IRRIGATION, 0x06.toByte()),

    // Irrigation System Config Report:supporting -> controlling
    IRRIGATION_SYSTEM_CONFIG_REPORT(CommandClassID.IRRIGATION, 0x07.toByte()),

    // Irrigation Valve Info Get:controlling -> supporting
    IRRIGATION_VALVE_INFO_GET(CommandClassID.IRRIGATION, 0x08.toByte()),

    // Irrigation Valve Info Report:supporting -> controlling
    IRRIGATION_VALVE_INFO_REPORT(CommandClassID.IRRIGATION, 0x09.toByte()),

    // Irrigation Valve Config Set:controlling -> supporting
    IRRIGATION_VALVE_CONFIG_SET(CommandClassID.IRRIGATION, 0x0A.toByte()),

    // Irrigation Valve Config Get:controlling -> supporting
    IRRIGATION_VALVE_CONFIG_GET(CommandClassID.IRRIGATION, 0x0B.toByte()),

    // Irrigation Valve Config Report:supporting -> controlling
    IRRIGATION_VALVE_CONFIG_REPORT(CommandClassID.IRRIGATION, 0x0C.toByte()),

    // Irrigation Valve Run:controlling -> supporting
    IRRIGATION_VALVE_RUN(CommandClassID.IRRIGATION, 0x0D.toByte()),

    // Irrigation Valve Table Set:controlling -> supporting
    IRRIGATION_VALVE_TABLE_SET(CommandClassID.IRRIGATION, 0x0E.toByte()),

    // Irrigation Valve Table Get:controlling -> supporting
    IRRIGATION_VALVE_TABLE_GET(CommandClassID.IRRIGATION, 0x0F.toByte()),

    // Irrigation Valve Table Report:supporting -> controlling
    IRRIGATION_VALVE_TABLE_REPORT(CommandClassID.IRRIGATION, 0x10.toByte()),

    // Irrigation Valve Table Run:controlling -> supporting
    IRRIGATION_VALVE_TABLE_RUN(CommandClassID.IRRIGATION, 0x11.toByte()),

    // Irrigation System Shutoff:controlling -> supporting
    IRRIGATION_SYSTEM_SHUTOFF(CommandClassID.IRRIGATION, 0x12.toByte()),

    // Language Set:controlling -> supporting
    LANGUAGE_SET(CommandClassID.LANGUAGE, 0x01.toByte()),

    // Language Get:controlling -> supporting
    LANGUAGE_GET(CommandClassID.LANGUAGE, 0x02.toByte()),

    // Language Report:supporting -> controlling
    LANGUAGE_REPORT(CommandClassID.LANGUAGE, 0x03.toByte()),

    // Lock Set:controlling -> supporting
    LOCK_SET(CommandClassID.LOCK, 0x01.toByte()),

    // Lock Get:controlling -> supporting
    LOCK_GET(CommandClassID.LOCK, 0x02.toByte()),

    // Lock Report:supporting -> controlling
    LOCK_REPORT(CommandClassID.LOCK, 0x03.toByte()),

    // Mailbox Configuration Get:controlling -> supporting
    MAILBOX_CONFIGURATION_GET(CommandClassID.MAILBOX, 0x01.toByte()),

    // Mailbox Configuration Set:controlling -> supporting
    MAILBOX_CONFIGURATION_SET(CommandClassID.MAILBOX, 0x02.toByte()),

    // Mailbox Configuration Report:supporting -> controlling
    MAILBOX_CONFIGURATION_REPORT(CommandClassID.MAILBOX, 0x03.toByte()),

    // Mailbox Queue:supporting -> supporting
    MAILBOX_QUEUE(CommandClassID.MAILBOX, 0x04.toByte()),

    // Mailbox Wake Up Notification:supporting -> supporting
    MAILBOX_WAKEUP_NOTIFICATION(CommandClassID.MAILBOX, 0x05.toByte()),

    // Mailbox Failling Node:supporting -> supporting
    MAILBOX_NODE_FAILING(CommandClassID.MAILBOX, 0x06.toByte()),

    // Manufacturer Specific Get:controlling -> supporting
    MANUFACTURER_SPECIFIC_GET(CommandClassID.MANUFACTURER_SPECIFIC, 0x04.toByte()),

    // Manufacturer Specific Report:supporting -> controlling
    MANUFACTURER_SPECIFIC_REPORT(CommandClassID.MANUFACTURER_SPECIFIC, 0x05.toByte()),

    // Device Specific Get:controlling -> supporting
    DEVICE_SPECIFIC_GET(CommandClassID.MANUFACTURER_SPECIFIC, 0x06.toByte()),

    // Device Specific Report:supporting -> controlling
    DEVICE_SPECIFIC_REPORT(CommandClassID.MANUFACTURER_SPECIFIC, 0x07.toByte()),

    // Meter Get:controlling -> supporting
    METER_GET(CommandClassID.METER, 0x01.toByte()),

    // Meter Report:supporting -> controlling
    METER_REPORT(CommandClassID.METER, 0x02.toByte()),

    // Meter Supported Get:controlling -> supporting
    METER_SUPPORTED_GET(CommandClassID.METER, 0x03.toByte()),

    // Meter Supported Report:supporting -> controlling
    METER_SUPPORTED_REPORT(CommandClassID.METER, 0x04.toByte()),

    // Meter Reset:controlling -> supporting
    METER_RESET(CommandClassID.METER, 0x05.toByte()),

    // Meter Table Point Adm Number Set:controlling -> supporting
    METER_TBL_TABLE_POINT_ADM_NO_SET(CommandClassID.METER_TBL_CONFIG, 0x01.toByte()),

    // Meter Table Point Adm Number Get:controlling -> supporting
    METER_TBL_TABLE_POINT_ADM_NO_GET(CommandClassID.METER_TBL_MONITOR, 0x01.toByte()),

    // Meter Table Point Adm Number Report:supporting -> controlling
    METER_TBL_TABLE_POINT_ADM_NO_REPORT(CommandClassID.METER_TBL_MONITOR, 0x02.toByte()),

    // Meter Table ID Get:controlling -> supporting
    METER_TBL_TABLE_ID_GET(CommandClassID.METER_TBL_MONITOR, 0x03.toByte()),

    // Meter Table ID Report:supporting -> controlling
    METER_TBL_TABLE_ID_REPORT(CommandClassID.METER_TBL_MONITOR, 0x04.toByte()),

    // Meter Table Capability Get:controlling -> supporting
    METER_TBL_TABLE_CAPABILITY_GET(CommandClassID.METER_TBL_MONITOR, 0x05.toByte()),

    // Meter Table Capability Report:supporting -> controlling
    METER_TBL_REPORT(CommandClassID.METER_TBL_MONITOR, 0x06.toByte()),

    // Meter Table Status Supported Get:controlling -> supporting
    METER_TBL_STATUS_SUPPORTED_GET(CommandClassID.METER_TBL_MONITOR, 0x07.toByte()),

    // Meter Table Status Supported Report:supporting -> controlling
    METER_TBL_STATUS_SUPPORTED_REPORT(CommandClassID.METER_TBL_MONITOR, 0x08.toByte()),

    // Meter Table Status Depth Get:controlling -> supporting
    METER_TBL_STATUS_DEPTH_GET(CommandClassID.METER_TBL_MONITOR, 0x09.toByte()),

    // Meter Table Status Date Get:controlling -> supporting
    METER_TBL_STATUS_DATE_GET(CommandClassID.METER_TBL_MONITOR, 0x0A.toByte()),

    // Meter Table Status Report:supporting -> controlling
    METER_TBL_STATUS_REPORT(CommandClassID.METER_TBL_MONITOR, 0x0B.toByte()),

    // Meter Table Current Data Get:controlling -> supporting
    METER_TBL_CURRENT_DATA_GET(CommandClassID.METER_TBL_MONITOR, 0x0C.toByte()),

    // Meter Table Current Data Report:supporting -> controlling
    METER_TBL_CURRENT_DATA_REPORT(CommandClassID.METER_TBL_MONITOR, 0x0D.toByte()),

    // Meter Table Historical Data Get:controlling -> supporting
    METER_TBL_HISTORICAL_DATA_GET(CommandClassID.METER_TBL_MONITOR, 0x0E.toByte()),

    // Meter Table Historical Data Report:supporting -> controlling
    METER_TBL_HISTORICAL_DATA_REPORT(CommandClassID.METER_TBL_MONITOR, 0x0F.toByte()),

    // Meter Table Push Configuration Set:controlling -> supporting
    METER_TBL_PUSH_CONFIGURATION_SET(CommandClassID.METER_TBL_PUSH, 0x01.toByte()),

    // Meter Table Push Configuration Get:controlling -> supporting
    METER_TBL_PUSH_CONFIGURATION_GET(CommandClassID.METER_TBL_PUSH, 0x02.toByte()),

    // Meter Table Push Configuration Report:supporting -> controlling
    METER_TBL_PUSH_CONFIGURATION_REPORT(CommandClassID.METER_TBL_PUSH, 0x03.toByte()),

    // Move To Position Set:controlling -> supporting
    MOVE_TO_POSITION_SET(CommandClassID.MTP_WINDOW_COVERING, 0x01.toByte()),

    // Move To Position Get:controlling -> supporting
    MOVE_TO_POSITION_GET(CommandClassID.MTP_WINDOW_COVERING, 0x02.toByte()),

    // Move To Position Report:supporting -> controlling
    MOVE_TO_POSITION_REPORT(CommandClassID.MTP_WINDOW_COVERING, 0x03.toByte()),

    // Multi Channel End Point Get:controlling -> supporting
    MULTI_CHANNEL_END_POINT_GET(CommandClassID.MULTI_CHANNEL, 0x07.toByte()),

    // Multi Channel End Point Report:supporting -> controlling
    MULTI_CHANNEL_END_POINT_REPORT(CommandClassID.MULTI_CHANNEL, 0x08.toByte()),

    // Multi Channel Capability Get:controlling -> supporting
    MULTI_CHANNEL_CAPABILITY_GET(CommandClassID.MULTI_CHANNEL, 0x09.toByte()),

    // Multi Channel Capability Report:supporting -> controlling
    MULTI_CHANNEL_CAPABILITY_REPORT(CommandClassID.MULTI_CHANNEL, 0x0A.toByte()),

    // Multi Channel End Point Find:controlling -> supporting
    MULTI_CHANNEL_END_POINT_FIND(CommandClassID.MULTI_CHANNEL, 0x0B.toByte()),

    // Multi Channel End Point Find Report:supporting -> controlling
    MULTI_CHANNEL_END_POINT_FIND_REPORT(CommandClassID.MULTI_CHANNEL, 0x0C.toByte()),

    // Multi Channel Command Encapsulation:supporting -> controlling
    MULTI_CHANNEL_CMD_ENCAP(CommandClassID.MULTI_CHANNEL, 0x0D.toByte()),

    // Multi Channel Aggregated Members Get:controlling -> supporting
    MULTI_CHANNEL_AGGREGATED_MEMBERS_GET(CommandClassID.MULTI_CHANNEL, 0x0E.toByte()),

    // Multi Channel Aggregated Members Report:supporting -> controlling
    MULTI_CHANNEL_AGGREGATED_MEMBERS_REPORT(CommandClassID.MULTI_CHANNEL, 0x0F.toByte()),

    // Multi Channel Association Set:controlling -> supporting
    MULTI_CHANNEL_ASSOCIATION_SET(CommandClassID.MULTI_CHANNEL_ASSOCIATION, 0x01.toByte()),

    // Multi Channel Association Get:controlling -> supporting
    MULTI_CHANNEL_ASSOCIATION_GET(CommandClassID.MULTI_CHANNEL_ASSOCIATION, 0x02.toByte()),

    // Multi Channel Association Report:supporting -> controlling
    MULTI_CHANNEL_ASSOCIATION_REPORT(CommandClassID.MULTI_CHANNEL_ASSOCIATION, 0x03.toByte()),

    // Multi Channel Association Remove:controlling -> supporting
    MULTI_CHANNEL_ASSOCIATION_REMOVE(CommandClassID.MULTI_CHANNEL_ASSOCIATION, 0x04.toByte()),

    // Multi Channel Association Groupings Get:controlling -> supporting
    MULTI_CHANNEL_ASSOCIATION_GROUPINGS_GET(CommandClassID.MULTI_CHANNEL_ASSOCIATION, 0x05.toByte()),

    // Multi Channel Association Groupings Report:supporting -> controlling
    MULTI_CHANNEL_ASSOCIATION_GROUPINGS_REPORT(CommandClassID.MULTI_CHANNEL_ASSOCIATION, 0x06.toByte()),

    // Multi Command Encapsulated:controlling -> supporting
    MULTI_CMD_ENCAP(CommandClassID.MULTI_CMD, 0x01.toByte()),

    // Multilevel Sensor Get Supported Sensor:controlling -> supporting
    SENSOR_MULTILEVEL_SUPPORTED_GET_SENSOR(CommandClassID.SENSOR_MULTILEVEL, 0x01.toByte()),

    // Multilevel Sensor Supported Sensor Report:supporting -> controlling
    SENSOR_MULTILEVEL_SUPPORTED_SENSOR_REPORT(CommandClassID.SENSOR_MULTILEVEL, 0x02.toByte()),

    // Multilevel Sensor Get Supported Scale:controlling -> supporting
    SENSOR_MULTILEVEL_SUPPORTED_GET_SCALE(CommandClassID.SENSOR_MULTILEVEL, 0x03.toByte()),

    // Multilevel Sensor Get:controlling -> supporting
    SENSOR_MULTILEVEL_GET(CommandClassID.SENSOR_MULTILEVEL, 0x04.toByte()),

    // Multilevel Sensor Report:supporting -> controlling
    SENSOR_MULTILEVEL_REPORT(CommandClassID.SENSOR_MULTILEVEL, 0x05.toByte()),

    // Multilevel Sensor Supported Scale Report:supporting -> controlling
    SENSOR_MULTILEVEL_SUPPORTED_SCALE_REPORT(CommandClassID.SENSOR_MULTILEVEL, 0x06.toByte()),

    // Multilevel Switch Set:controlling -> supporting
    SWITCH_MULTILEVEL_SET(CommandClassID.SWITCH_MULTILEVEL, 0x01.toByte()),

    // Multilevel Switch Get:controlling -> supporting
    SWITCH_MULTILEVEL_GET(CommandClassID.SWITCH_MULTILEVEL, 0x02.toByte()),

    // Multilevel Switch Report:supporting -> controlling
    SWITCH_MULTILEVEL_REPORT(CommandClassID.SWITCH_MULTILEVEL, 0x03.toByte()),

    // Multilevel Switch Start Level Change:controlling -> supporting
    SWITCH_MULTILEVEL_START_LEVEL_CHANGE(CommandClassID.SWITCH_MULTILEVEL, 0x04.toByte()),

    // Multilevel Switch Stop Level Change:controlling -> supporting
    SWITCH_MULTILEVEL_STOP_LEVEL_CHANGE(CommandClassID.SWITCH_MULTILEVEL, 0x05.toByte()),

    // Multilevel Switch Supported Get:controlling -> supporting
    SWITCH_MULTILEVEL_SUPPORTED_GET(CommandClassID.SWITCH_MULTILEVEL, 0x06.toByte()),

    // Multilevel Switch Supported Report:supporting -> controlling
    SWITCH_MULTILEVEL_SUPPORTED_REPORT(CommandClassID.SWITCH_MULTILEVEL, 0x07.toByte()),

    // Multilevel Toggle Switch Set:controlling -> supporting
    SWITCH_TOGGLE_MULTILEVEL_SET(CommandClassID.SWITCH_TOGGLE_MULTILEVEL, 0x01.toByte()),

    // Multilevel Toggle Switch Get:controlling -> supporting
    SWITCH_TOGGLE_MULTILEVEL_GET(CommandClassID.SWITCH_TOGGLE_MULTILEVEL, 0x02.toByte()),

    // Multilevel Toggle Switch Report:supporting -> controlling
    SWITCH_TOGGLE_MULTILEVEL_REPORT(CommandClassID.SWITCH_TOGGLE_MULTILEVEL, 0x03.toByte()),

    // Multilevel Toggle Switch Start Level Change:controlling -> supporting
    SWITCH_TOGGLE_MULTILEVEL_START_LEVEL_CHANGE(CommandClassID.SWITCH_TOGGLE_MULTILEVEL, 0x04.toByte()),

    // Multilevel Toggle Switch Stop Level Change:controlling -> supporting
    SWITCH_TOGGLE_MULTILEVEL_STOP_LEVEL_CHANGE(CommandClassID.SWITCH_TOGGLE_MULTILEVEL, 0x05.toByte()),

    // Learn Mode Set:controlling -> supporting
    COMMAND_LEARN_MODE_SET(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x01.toByte()),

    // Learn Mode Set Status:supporting -> controlling
    COMMAND_LEARN_MODE_SET_STATUS(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x02.toByte()),

    // Network Update Request:controlling -> supporting
    COMMAND_NETWORK_UPDATE_REQUEST(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x03.toByte()),

    // Network Update Request Status:supporting -> controlling
    COMMAND_NETWORK_UPDATE_REQUEST_STATUS(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x04.toByte()),

    // Node Information Send:controlling -> supporting
    COMMAND_NODE_INFORMATION_SEND(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x05.toByte()),

    // Default Set:controlling -> supporting
    COMMAND_DEFAULT_SET(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x06.toByte()),

    // Default Set Complete:supporting -> controlling
    COMMAND_DEFAULT_SET_COMPLETE(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x07.toByte()),

    // DSK Get:controlling -> supporting
    COMMAND_DSK_GET(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x08.toByte()),

    // DSK Report:supporting -> controlling
    COMMAND_DSK_REPORT(CommandClassID.NETWORK_MANAGEMENT_BASIC, 0x09.toByte()),

    // Node Add:controlling -> supporting
    COMMAND_NODE_ADD(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x01.toByte()),

    // Node Add Status:supporting -> controlling
    COMMAND_NODE_ADD_STATUS(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x02.toByte()),

    // Node Remove:controlling -> supporting
    COMMAND_NODE_REMOVE(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x03.toByte()),

    // Node Remove Status:supporting -> controlling
    COMMAND_NODE_REMOVE_STATUS(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x04.toByte()),

    // Failed Node Remove:controlling -> supporting
    COMMAND_FAILED_NODE_REMOVE(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x07.toByte()),

    // Failed Node Remove Status:supporting -> controlling
    COMMAND_FAILED_NODE_REMOVE_STATUS(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x08.toByte()),

    // Failed Node Replace:controlling -> supporting
    COMMAND_FAILED_NODE_REPLACE(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x09.toByte()),

    // Failed Node Replace Status:supporting -> controlling
    COMMAND_FAILED_NODE_REPLACE_STATUS(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x0A.toByte()),

    // Node Neighbor Update Request:controlling -> supporting
    COMMAND_NODE_NEIGHBOR_UPDATE_REQUEST(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x0B.toByte()),

    // Node Neighbor Update Status:supporting -> controlling
    COMMAND_NODE_NEIGHBOR_UPDATE_STATUS(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x0C.toByte()),

    // Return Route Assign:controlling -> supporting
    COMMAND_RETURN_ROUTE_ASSIGN(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x0D.toByte()),

    // Return Route Assign Complete:supporting -> controlling
    COMMAND_RETURN_ROUTE_ASSIGN_COMPLETE(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x0E.toByte()),

    // Return Route Delete:controlling -> supporting
    COMMAND_RETURN_ROUTE_DELETE(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x0F.toByte()),

    // Return Route Delete Complete:supporting -> controlling
    COMMAND_RETURN_ROUTE_DELETE_COMPLETE(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x10.toByte()),

    // Node Add Keys Report:supporting -> controlling
    COMMAND_NODE_ADD_KEYS_REPORT(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x11.toByte()),

    // Node Add Keys Set:controlling -> supporting
    COMMAND_NODE_ADD_KEYS_SET(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x12.toByte()),

    // Node Add DSK Report:supporting -> controlling
    COMMAND_NODE_ADD_DSK_REPORT(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x13.toByte()),

    // Node Add DSK Set:controlling -> supporting
    COMMAND_NODE_ADD_DSK_SET(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x14.toByte()),

    // Included Node Information Frame Report:supporting -> controlling
    COMMAND_INCLUDED_NIF_REPORT(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x19.toByte()),

    // Smart Start Join Started:supporting -> controlling
    COMMAND_SMART_START_JOIN_STARTED_REPORT(CommandClassID.NETWORK_MANAGEMENT_INCLUSION, 0x15.toByte()),

    // Last Working Route Set:controlling -> supporting
    LAST_WORKING_ROUTE_SET(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x01.toByte()),

    // Last Working Route Get:controlling -> supporting
    LAST_WORKING_ROUTE_GET(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x02.toByte()),

    // Last Working Route Report:supporting -> controlling
    LAST_WORKING_ROUTE_REPORT(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x03.toByte()),

    // Statistics Get:controlling -> supporting
    STATISTICS_GET(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x04.toByte()),

    // Statistics Report:supporting -> controlling
    STATISTICS_REPORT(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x05.toByte()),

    // Statistics Clear:controlling -> supporting
    STATISTICS_CLEAR(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x06.toByte()),

    // RSSI Get:controlling -> supporting
    COMMAND_RSSI_GET(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x07.toByte()),

    // RSSI Report:supporting -> controlling
    COMMAND_RSSI_REPORT(CommandClassID.NETWORK_MANAGEMENT_INSTALLATION_MAINTENANCE, 0x08.toByte()),

    // Controller Change:controlling -> supporting
    COMMAND_CONTROLLER_CHANGE(CommandClassID.NETWORK_MANAGEMENT_PRIMARY, 0x01.toByte()),

    // Controller Change Status:supporting -> controlling
    COMMAND_CONTROLLER_CHANGE_STATUS(CommandClassID.NETWORK_MANAGEMENT_PRIMARY, 0x02.toByte()),

    // Node List Get:controlling -> supporting
    COMMAND_NODE_LIST_GET(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x01.toByte()),

    // Node List Report:supporting -> controlling
    COMMAND_NODE_LIST_REPORT(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x02.toByte()),

    // Node List Cached Get:controlling -> supporting
    COMMAND_NODE_INFO_CACHED_GET(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x03.toByte()),

    // Node List Cached Report:supporting -> controlling
    COMMAND_NODE_INFO_CACHED_REPORT(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x04.toByte()),

    // Network Management Multi Channel End Point Get:controlling -> supporting
    NM_MULTI_CHANNEL_END_POINT_GET(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x05.toByte()),

    // Network Management Multi Channel End Point Report:supporting -> controlling
    NM_MULTI_CHANNEL_END_POINT_REPORT(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x06.toByte()),

    // Network Management Multi Channel Capability Get:controlling -> supporting
    NM_MULTI_CHANNEL_CAPABILITY_GET(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x07.toByte()),

    // Network Management Multi Channel Capability Report:supporting -> controlling
    NM_MULTI_CHANNEL_CAPABILITY_REPORT(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x08.toByte()),

    // Network Management Multi Channel Aggregated Members Get:controlling -> supporting
    NM_MULTI_CHANNEL_AGGREGATED_MEMBERS_GET(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x09.toByte()),

    // Network Management Multi Channel Aggregated Members Report:supporting -> controlling
    NM_MULTI_CHANNEL_AGGREGATED_MEMBERS_REPORT(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x0A.toByte()),

    // Failed Node List Get:controlling -> supporting
    COMMAND_FAILED_NODE_LIST_GET(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x0B.toByte()),

    // Failed Node List Report:supporting -> controlling
    COMMAND_FAILED_NODE_LIST_REPORT(CommandClassID.NETWORK_MANAGEMENT_PROXY, 0x0C.toByte()),

    // Node Name Set:controlling -> supporting
    NODE_NAMING_NODE_NAME_SET(CommandClassID.NODE_NAMING, 0x01.toByte()),

    // Node Name Get:controlling -> supporting
    NODE_NAMING_NODE_NAME_GET(CommandClassID.NODE_NAMING, 0x02.toByte()),

    // Node Name Report:supporting -> controlling
    NODE_NAMING_NODE_NAME_REPORT(CommandClassID.NODE_NAMING, 0x03.toByte()),

    // Node Location Set:controlling -> supporting
    NODE_NAMING_NODE_LOCATION_SET(CommandClassID.NODE_NAMING, 0x04.toByte()),

    // Node Location Get:controlling -> supporting
    NODE_NAMING_NODE_LOCATION_GET(CommandClassID.NODE_NAMING, 0x05.toByte()),

    // Node Location Report:supporting -> controlling
    NODE_NAMING_NODE_LOCATION_REPORT(CommandClassID.NODE_NAMING, 0x06.toByte()),

    // Node Provisioning Set:controlling -> supporting
    COMMAND_NODE_PROVISIONING_SET(CommandClassID.NODE_PROVISIONING, 0x01.toByte()),

    // Node Provisioning Delete:controlling -> supporting
    COMMAND_NODE_PROVISIONING_DELETE(CommandClassID.NODE_PROVISIONING, 0x02.toByte()),

    // Node Provisioning Get:controlling -> supporting
    COMMAND_NODE_PROVISIONING_GET(CommandClassID.NODE_PROVISIONING, 0x05.toByte()),

    // Node Provisioning Report:supporting -> controlling
    COMMAND_NODE_PROVISIONING_REPORT(CommandClassID.NODE_PROVISIONING, 0x06.toByte()),

    // Node Provisioning List Iteration Get:controlling -> supporting
    COMMAND_NODE_PROVISIONING_LIST_ITERATION_GET(CommandClassID.NODE_PROVISIONING, 0x03.toByte()),

    // Node Provisioning List Iteration Report:supporting -> controlling
    COMMAND_NODE_PROVISIONING_LIST_ITERATION_REPORT(CommandClassID.NODE_PROVISIONING, 0x04.toByte()),

    // Event Supported Get:controlling -> supporting
    EVENT_SUPPORTED_GET(CommandClassID.NOTIFICATION, 0x01.toByte()),

    // Event Supported Report:supporting -> controlling
    EVENT_SUPPORTED_REPORT(CommandClassID.NOTIFICATION, 0x02.toByte()),

    // Notification Get:controlling -> supporting
    NOTIFICATION_GET(CommandClassID.NOTIFICATION, 0x04.toByte()),

    // Notification Report:supporting -> controlling
    NOTIFICATION_REPORT(CommandClassID.NOTIFICATION, 0x05.toByte()),

    // Notification Set:controlling -> supporting
    NOTIFICATION_SET(CommandClassID.NOTIFICATION, 0x06.toByte()),

    // Notification Supported Get:controlling -> supporting
    NOTIFICATION_SUPPORTED_GET(CommandClassID.NOTIFICATION, 0x07.toByte()),

    // Notification Supported Report:supporting -> controlling
    NOTIFICATION_SUPPORTED_REPORT(CommandClassID.NOTIFICATION, 0x08.toByte()),

    // Powerlevel Set:controlling -> supporting
    POWERLEVEL_SET(CommandClassID.POWERLEVEL, 0x01.toByte()),

    // Powerlevel Get:controlling -> supporting
    POWERLEVEL_GET(CommandClassID.POWERLEVEL, 0x02.toByte()),

    // Powerlevel Report:supporting -> controlling
    POWERLEVEL_REPORT(CommandClassID.POWERLEVEL, 0x03.toByte()),

    // Powerlevel Test Node Set:controlling -> supporting
    POWERLEVEL_TEST_NODE_SET(CommandClassID.POWERLEVEL, 0x04.toByte()),

    // Powerlevel Test Node Get:controlling -> supporting
    POWERLEVEL_TEST_NODE_GET(CommandClassID.POWERLEVEL, 0x05.toByte()),

    // Powerlevel Test Node Report:supporting -> controlling
    POWERLEVEL_TEST_NODE_REPORT(CommandClassID.POWERLEVEL, 0x06.toByte()),

    // Prepayment Balance Get:controlling -> supporting
    PREPAYMENT_BALANCE_GET(CommandClassID.PREPAYMENT, 0x01.toByte()),

    // Prepayment Balance Report:supporting -> controlling
    PREPAYMENT_BALANCE_REPORT(CommandClassID.PREPAYMENT, 0x02.toByte()),

    // Prepayment Supported Get:controlling -> supporting
    PREPAYMENT_SUPPORTED_GET(CommandClassID.PREPAYMENT, 0x03.toByte()),

    // Prepayment Supported Report:supporting -> controlling
    PREPAYMENT_SUPPORTED_REPORT(CommandClassID.PREPAYMENT, 0x04.toByte()),

    // Prepayment Encapsulation:controlling -> supporting
    CMD_ENCAPSULATION(CommandClassID.PREPAYMENT_ENCAPSULATION, 0x01.toByte()),

    // Proprietary Set:controlling -> supporting
    PROPRIETARY_SET(CommandClassID.PROPRIETARY, 0x01.toByte()),

    // Proprietary Get:controlling -> supporting
    PROPRIETARY_GET(CommandClassID.PROPRIETARY, 0x02.toByte()),

    // Proprietary Report:supporting -> controlling
    PROPRIETARY_REPORT(CommandClassID.PROPRIETARY, 0x03.toByte()),

    // Protection Set:controlling -> supporting
    PROTECTION_SET(CommandClassID.PROTECTION, 0x01.toByte()),

    // Protection Get:controlling -> supporting
    PROTECTION_GET(CommandClassID.PROTECTION, 0x02.toByte()),

    // Protection Report:supporting -> controlling
    PROTECTION_REPORT(CommandClassID.PROTECTION, 0x03.toByte()),

    // Protection Supported Get:controlling -> supporting
    PROTECTION_SUPPORTED_GET(CommandClassID.PROTECTION, 0x04.toByte()),

    // Protection Supported Report:supporting -> controlling
    PROTECTION_SUPPORTED_REPORT(CommandClassID.PROTECTION, 0x05.toByte()),

    // Protection Exclusive Control Set:controlling -> supporting
    PROTECTION_EC_SET(CommandClassID.PROTECTION, 0x06.toByte()),

    // Protection Exclusive Control Get:controlling -> supporting
    PROTECTION_EC_GET(CommandClassID.PROTECTION, 0x07.toByte()),

    // Protection Exclusive Control Report:supporting -> controlling
    PROTECTION_EC_REPORT(CommandClassID.PROTECTION, 0x08.toByte()),

    // Protection Timeout Set:controlling -> supporting
    PROTECTION_TIMEOUT_SET(CommandClassID.PROTECTION, 0x09.toByte()),

    // Protection Timeout Get:controlling -> supporting
    PROTECTION_TIMEOUT_GET(CommandClassID.PROTECTION, 0x0A.toByte()),

    // Protection Timeout Report:supporting -> controlling
    PROTECTION_TIMEOUT_REPORT(CommandClassID.PROTECTION, 0x0B.toByte()),

    // Pulse Meter Get:controlling -> supporting
    METER_PULSE_GET(CommandClassID.METER_PULSE, 0x04.toByte()),

    // Pulse Meter Report:supporting -> controlling
    METER_PULSE_REPORT(CommandClassID.METER_PULSE, 0x05.toByte()),

    // Rate Table Set:controlling -> supporting
    RATE_TBL_SET(CommandClassID.RATE_TBL_CONFIG, 0x01.toByte()),

    // Rate Table Remove:controlling -> supporting
    RATE_TBL_REMOVE(CommandClassID.RATE_TBL_CONFIG, 0x02.toByte()),

    // Rate Table Supported Get:controlling -> supporting
    RATE_TBL_SUPPORTED_GET(CommandClassID.RATE_TBL_MONITOR, 0x01.toByte()),

    // Rate Table Supported Report:supporting -> controlling
    RATE_TBL_SUPPORTED_REPORT(CommandClassID.RATE_TBL_MONITOR, 0x02.toByte()),

    // Rate Table Get:controlling -> supporting
    RATE_TBL_GET(CommandClassID.RATE_TBL_MONITOR, 0x03.toByte()),

    // Rate Table Report:supporting -> controlling
    RATE_TBL_REPORT(CommandClassID.RATE_TBL_MONITOR, 0x04.toByte()),

    // Rate Table Active Rate Get:controlling -> supporting
    RATE_TBL_ACTIVE_RATE_GET(CommandClassID.RATE_TBL_MONITOR, 0x05.toByte()),

    // Rate Table Active Rate Report:supporting -> controlling
    RATE_TBL_ACTIVE_RATE_REPORT(CommandClassID.RATE_TBL_MONITOR, 0x06.toByte()),

    // Rate Table Current Data Get:controlling -> supporting
    RATE_TBL_CURRENT_DATA_GET(CommandClassID.RATE_TBL_MONITOR, 0x07.toByte()),

    // Rate Table Current Data Report:supporting -> controlling
    RATE_TBL_CURRENT_DATA_REPORT(CommandClassID.RATE_TBL_MONITOR, 0x08.toByte()),

    // Rate Table Historical Data Get:controlling -> supporting
    RATE_TBL_HISTORICAL_DATA_GET(CommandClassID.RATE_TBL_MONITOR, 0x09.toByte()),

    // Rate Table Historical Data Report:supporting -> controlling
    RATE_TBL_HISTORICAL_DATA_REPORT(CommandClassID.RATE_TBL_MONITOR, 0x0A.toByte()),

    // Remote Association Activate:controlling -> supporting
    REMOTE_ASSOCIATION_ACTIVATE(CommandClassID.REMOTE_ASSOCIATION_ACTIVATE, 0x01.toByte()),

    // Remote Association Configuration Set:controlling -> supporting
    REMOTE_ASSOCIATION_CONFIGURATION_SET(CommandClassID.REMOTE_ASSOCIATION, 0x01.toByte()),

    // Remote Association Configuration Get:controlling -> supporting
    REMOTE_ASSOCIATION_CONFIGURATION_GET(CommandClassID.REMOTE_ASSOCIATION, 0x02.toByte()),

    // Remote Association Configuration Report:supporting -> controlling
    REMOTE_ASSOCIATION_CONFIGURATION_REPORT(CommandClassID.REMOTE_ASSOCIATION, 0x03.toByte()),

    // Scene Activation Set:controlling -> supporting
    SCENE_ACTIVATION_SET(CommandClassID.SCENE_ACTIVATION, 0x01.toByte()),

    // Scene Actuator Configuration Set:controlling -> supporting
    SCENE_ACTUATOR_CONF_SET(CommandClassID.SCENE_ACTUATOR_CONF, 0x01.toByte()),

    // Scene Actuator Configuration Get:controlling -> supporting
    SCENE_ACTUATOR_CONF_GET(CommandClassID.SCENE_ACTUATOR_CONF, 0x02.toByte()),

    // Scene Actuator Configuration Report:supporting -> controlling
    SCENE_ACTUATOR_CONF_REPORT(CommandClassID.SCENE_ACTUATOR_CONF, 0x03.toByte()),

    // Scene Controller Configuration Set:controlling -> supporting
    SCENE_CONTROLLER_CONF_SET(CommandClassID.SCENE_CONTROLLER_CONF, 0x01.toByte()),

    // Scene Controller Configuration Get:controlling -> supporting
    SCENE_CONTROLLER_CONF_GET(CommandClassID.SCENE_CONTROLLER_CONF, 0x02.toByte()),

    // Scene Controller Configuration Report:supporting -> controlling
    SCENE_CONTROLLER_CONF_REPORT(CommandClassID.SCENE_CONTROLLER_CONF, 0x03.toByte()),

    // Schedule Supported Get:controlling -> supporting
    SCHEDULE_SUPPORTED_GET(CommandClassID.SCHEDULE, 0x01.toByte()),

    // Schedule Supported Report:supporting -> controlling
    SCHEDULE_SUPPORTED_REPORT(CommandClassID.SCHEDULE, 0x02.toByte()),

    // Schedule Set:controlling -> supporting
    SCHEDULE_SET(CommandClassID.SCHEDULE, 0x03.toByte()),

    // Schedule Get:controlling -> supporting
    SCHEDULE_GET(CommandClassID.SCHEDULE, 0x04.toByte()),

    // Schedule Report:supporting -> controlling
    SCHEDULE_REPORT(CommandClassID.SCHEDULE, 0x05.toByte()),

    // Schedule Remove:controlling -> supporting
    SCHEDULE_REMOVE(CommandClassID.SCHEDULE, 0x06.toByte()),

    // Schedule State Set:controlling -> supporting
    SCHEDULE_STATE_SET(CommandClassID.SCHEDULE, 0x07.toByte()),

    // Schedule State Get:controlling -> supporting
    SCHEDULE_STATE_GET(CommandClassID.SCHEDULE, 0x08.toByte()),

    // Schedule State Report:supporting -> controlling
    SCHEDULE_STATE_REPORT(CommandClassID.SCHEDULE, 0x09.toByte()),

    // Schedule Supported Commands Get:controlling -> supporting
    SCHEDULE_SUPPORTED_COMMANDS_GET(CommandClassID.SCHEDULE, 0x0A.toByte()),

    // Schedule Supported Commands Report:supporting -> controlling
    SCHEDULE_SUPPORTED_COMMANDS_REPORT(CommandClassID.SCHEDULE, 0x0B.toByte()),

    // Schedule Entry Lock Enable Set:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_ENABLE_SET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x01.toByte()),

    // Schedule Entry Lock Enable All Set:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_ENABLE_ALL_SET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x02.toByte()),

    // Schedule Entry Lock Week Day Schedule Set:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_WEEK_DAY_SET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x03.toByte()),

    // Schedule Entry Lock Week Days Schedule Get:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_WEEK_DAY_GET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x04.toByte()),

    // Schedule Entry Lock Week Day Schedule Report:supporting -> controlling
    SCHEDULE_ENTRY_LOCK_WEEK_DAY_REPORT(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x05.toByte()),

    // Schedule Entry Lock Year Day Schedule Set:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_YEAR_DAY_SET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x06.toByte()),

    // Schedule Entry Lock Year Day Schedule Get:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_YEAR_DAY_GET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x07.toByte()),

    // Schedule Entry Lock Year Day Schedule Report:supporting -> controlling
    SCHEDULE_ENTRY_LOCK_YEAR_DAY_REPORT(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x08.toByte()),

    // Schedule Entry Lock Supported Get:controlling -> supporting
    SCHEDULE_ENTRY_TYPE_SUPPORTED_GET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x09.toByte()),

    // Schedule Entry Lock Supported Report:supporting -> controlling
    SCHEDULE_ENTRY_TYPE_SUPPORTED_REPORT(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x0A.toByte()),

    // Schedule Entry Lock Time Offset Get:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_TIME_OFFSET_GET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x0B.toByte()),

    // Schedule Entry Lock Time Offset Report:supporting -> controlling
    SCHEDULE_ENTRY_LOCK_TIME_OFFSET_REPORT(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x0C.toByte()),

    // Schedule Entry Lock Time Offset Set:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_TIME_OFFSET_SET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x0D.toByte()),

    // Schedule Entry Lock Daily Repeating Get:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_DAILY_REPEATING_GET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x0E.toByte()),

    // Schedule Entry Lock Daily Repeating Report:supporting -> controlling
    SCHEDULE_ENTRY_LOCK_DAILY_REPEATING_REPORT(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x0F.toByte()),

    // Schedule Entry Lock Daily Repeating Set:controlling -> supporting
    SCHEDULE_ENTRY_LOCK_DAILY_REPEATING_SET(CommandClassID.SCHEDULE_ENTRY_LOCK, 0x10.toByte()),

    // Screen Attributes Get:controlling -> supporting
    SCREEN_ATTRIBUTES_GET(CommandClassID.SCREEN_ATTRIBUTES, 0x01.toByte()),

    // Screen Attributes Report:supporting -> controlling
    SCREEN_ATTRIBUTES_REPORT(CommandClassID.SCREEN_ATTRIBUTES, 0x02.toByte()),

    // Screen Meta Data Get:controlling -> supporting
    SCREEN_MD_GET(CommandClassID.SCREEN_MD, 0x01.toByte()),

    // Screen Meta Data Report:supporting -> controlling
    SCREEN_MD_REPORT(CommandClassID.SCREEN_MD, 0x02.toByte()),

    // Security Commands Supported Get:controlling -> supporting
    SECURITY_COMMANDS_SUPPORTED_GET(CommandClassID.SECURITY, 0x02.toByte()),

    // Security Commands Supported Report:supporting -> controlling
    SECURITY_COMMANDS_SUPPORTED_REPORT(CommandClassID.SECURITY, 0x03.toByte()),

    // Security Scheme Get:controlling -> supporting
    SECURITY_SCHEME_GET(CommandClassID.SECURITY, 0x04.toByte()),

    // Security Scheme Report:supporting -> controlling
    SECURITY_SCHEME_REPORT(CommandClassID.SECURITY, 0x05.toByte()),

    // Network Key Set:controlling -> supporting
    NETWORK_KEY_SET(CommandClassID.SECURITY, 0x06.toByte()),

    // Network Key Verify:supporting -> controlling
    NETWORK_KEY_VERIFY(CommandClassID.SECURITY, 0x07.toByte()),

    // Security Scheme Inherit:controlling -> supporting
    SECURITY_SCHEME_INHERIT(CommandClassID.SECURITY, 0x08.toByte()),

    // Nonce Challenge Request:supporting -> supporting
    SECURITY_NONCE_GET(CommandClassID.SECURITY, 0x40.toByte()),

    // Nonce Challenge Response:supporting -> supporting
    SECURITY_NONCE_REPORT(CommandClassID.SECURITY, 0x80.toByte()),

    // Security Message Encapsulation:supporting -> supporting
    SECURITY_MESSAGE_ENCAPSULATION(CommandClassID.SECURITY, 0x81.toByte()),

    // Security Message Encapsulation:supporting -> supporting
    SECURITY_MESSAGE_ENCAPSULATION_NONCE_GET(CommandClassID.SECURITY, 0xC1.toByte()),

    // Security 2 Nonce Get:controlling -> supporting
    SECURITY_2_NONCE_GET(CommandClassID.SECURITY_2, 0x01.toByte()),

    // Security 2 Nonce Report:supporting -> controlling
    SECURITY_2_NONCE_REPORT(CommandClassID.SECURITY_2, 0x02.toByte()),

    // Security 2 Message Encapsulation:supporting -> supporting
    SECURITY_2_MESSAGE_ENCAPSULATION(CommandClassID.SECURITY_2, 0x03.toByte()),

    // Security 2 KEX Get:controlling -> supporting
    KEX_GET(CommandClassID.SECURITY_2, 0x04.toByte()),

    // Security 2 KEX Report:supporting -> controlling
    KEX_REPORT(CommandClassID.SECURITY_2, 0x05.toByte()),

    // Security 2 KEX Set:controlling -> supporting
    KEX_SET(CommandClassID.SECURITY_2, 0x06.toByte()),

    // Security 2 KEX Fail:supporting -> supporting
    KEX_FAIL(CommandClassID.SECURITY_2, 0x07.toByte()),

    // Security 2 Public Key Report:supporting -> supporting
    PUBLIC_KEY_REPORT(CommandClassID.SECURITY_2, 0x08.toByte()),

    // Security 2 Network Key Get:supporting -> controlling
    SECURITY_2_NETWORK_KEY_GET(CommandClassID.SECURITY_2, 0x09.toByte()),

    // Security 2 Network Key Report:controlling -> supporting
    SECURITY_2_NETWORK_KEY_REPORT(CommandClassID.SECURITY_2, 0x0A.toByte()),

    // Security 2 Network Key Verify:supporting -> controlling
    SECURITY_2_NETWORK_KEY_VERIFY(CommandClassID.SECURITY_2, 0x0B.toByte()),

    // Security 2 Transfer End:supporting -> supporting
    SECURITY_2_TRANSFER_END(CommandClassID.SECURITY_2, 0x0C.toByte()),

    // Security 2 Commands Supported Get :controlling -> supporting
    SECURITY_2_COMMANDS_SUPPORTED_GET(CommandClassID.SECURITY_2, 0x0D.toByte()),

    // Security 2 Commands Supported Report:supporting -> controlling
    SECURITY_2_COMMANDS_SUPPORTED_REPORT(CommandClassID.SECURITY_2, 0x0E.toByte()),

    // Sensor Trigger Level Set:controlling -> supporting
    SENSOR_TRIGGER_LEVEL_SET(CommandClassID.SENSOR_CONFIGURATION, 0x01.toByte()),

    // Sensor Trigger Level Get:controlling -> supporting
    SENSOR_TRIGGER_LEVEL_GET(CommandClassID.SENSOR_CONFIGURATION, 0x02.toByte()),

    // Sensor Trigger Level Report:supporting -> controlling
    SENSOR_TRIGGER_LEVEL_REPORT(CommandClassID.SENSOR_CONFIGURATION, 0x03.toByte()),

    // Simple AV Control Set:controlling -> supporting
    SIMPLE_AV_CONTROL_SET(CommandClassID.SIMPLE_AV_CONTROL, 0x01.toByte()),

    // Simple AV Control Get:controlling -> supporting
    SIMPLE_AV_CONTROL_GET(CommandClassID.SIMPLE_AV_CONTROL, 0x02.toByte()),

    // Simple AV Control Report:supporting -> controlling
    SIMPLE_AV_CONTROL_REPORT(CommandClassID.SIMPLE_AV_CONTROL, 0x03.toByte()),

    // Simple AV Control Supported Get:controlling -> supporting
    SIMPLE_AV_CONTROL_SUPPORTED_GET(CommandClassID.SIMPLE_AV_CONTROL, 0x04.toByte()),

    // Simple AV Control Supported Report:supporting -> controlling
    SIMPLE_AV_CONTROL_SUPPORTED_REPORT(CommandClassID.SIMPLE_AV_CONTROL, 0x05.toByte()),

    // Sound Switch Tones Number Get:controlling -> supporting
    SOUND_SWITCH_TONES_NUMBER_GET(CommandClassID.SOUND_SWITCH, 0x01.toByte()),

    // Sound Switch Tones Number Report:supporting -> controlling
    SOUND_SWITCH_TONES_NUMBER_REPORT(CommandClassID.SOUND_SWITCH, 0x02.toByte()),

    // Sound Switch Tone Info Get:controlling -> supporting
    SOUND_SWITCH_TONE_INFO_GET(CommandClassID.SOUND_SWITCH, 0x03.toByte()),

    // Sound Switch Tone Info Report:supporting -> controlling
    SOUND_SWITCH_TONE_INFO_REPORT(CommandClassID.SOUND_SWITCH, 0x04.toByte()),

    // Sound Switch Configuration Set:controlling -> supporting
    SOUND_SWITCH_CONFIGURATION_SET(CommandClassID.SOUND_SWITCH, 0x05.toByte()),

    // Sound Switch Configuration Get:controlling -> supporting
    SOUND_SWITCH_CONFIGURATION_GET(CommandClassID.SOUND_SWITCH, 0x06.toByte()),

    // Sound Switch Configuration Report:supporting -> controlling
    SOUND_SWITCH_CONFIGURATION_REPORT(CommandClassID.SOUND_SWITCH, 0x07.toByte()),

    // Sound Switch Tone Play Set:controlling -> supporting
    SOUND_SWITCH_TONE_PLAY_SET(CommandClassID.SOUND_SWITCH, 0x08.toByte()),

    // Sound Switch Tone Play Get:controlling -> supporting
    SOUND_SWITCH_TONE_PLAY_GET(CommandClassID.SOUND_SWITCH, 0x09.toByte()),

    // Sound Switch Tone Play Report:supporting -> controlling
    SOUND_SWITCH_TONE_PLAY_REPORT(CommandClassID.SOUND_SWITCH, 0x0A.toByte()),

    // Supervision Get:controlling -> supporting
    SUPERVISION_GET(CommandClassID.SUPERVISION, 0x01.toByte()),

    // Supervision Report:supporting -> controlling
    SUPERVISION_REPORT(CommandClassID.SUPERVISION, 0x02.toByte()),

    // Tariff Table Supplier Set:controlling -> supporting
    TARIFF_TBL_SUPPLIER_SET(CommandClassID.TARIFF_CONFIG, 0x01.toByte()),

    // Tariff Table Set:controlling -> supporting
    TARIFF_TBL_SET(CommandClassID.TARIFF_CONFIG, 0x02.toByte()),

    // Tariff Table Remove:controlling -> supporting
    TARIFF_TBL_REMOVE(CommandClassID.TARIFF_CONFIG, 0x03.toByte()),

    // Tariff Table Supplier Get:controlling -> supporting
    TARIFF_TBL_SUPPLIER_GET(CommandClassID.TARIFF_TBL_MONITOR, 0x01.toByte()),

    // Tariff Table Supplier Report:supporting -> controlling
    TARIFF_TBL_SUPPLIER_REPORT(CommandClassID.TARIFF_TBL_MONITOR, 0x02.toByte()),

    // Tariff Table Get:controlling -> supporting
    TARIFF_TBL_GET(CommandClassID.TARIFF_TBL_MONITOR, 0x03.toByte()),

    // Tariff Table Report:supporting -> controlling
    TARIFF_TBL_REPORT(CommandClassID.TARIFF_TBL_MONITOR, 0x04.toByte()),

    // Tariff Table Cost Get:controlling -> supporting
    TARIFF_TBL_COST_GET(CommandClassID.TARIFF_TBL_MONITOR, 0x05.toByte()),

    // Tariff Table Cost Report:supporting -> controlling
    TARIFF_TBL_COST_REPORT(CommandClassID.TARIFF_TBL_MONITOR, 0x06.toByte()),

    // Thermostat Fan Mode Set:controlling -> supporting
    THERMOSTAT_FAN_MODE_SET(CommandClassID.THERMOSTAT_FAN_MODE, 0x01.toByte()),

    // Thermostat Fan Mode Get:controlling -> supporting
    THERMOSTAT_FAN_MODE_GET(CommandClassID.THERMOSTAT_FAN_MODE, 0x02.toByte()),

    // Thermostat Fan Mode Report:supporting -> controlling
    THERMOSTAT_FAN_MODE_REPORT(CommandClassID.THERMOSTAT_FAN_MODE, 0x03.toByte()),

    // Thermostat Fan Mode Supported Get:controlling -> supporting
    THERMOSTAT_FAN_MODE_SUPPORTED_GET(CommandClassID.THERMOSTAT_FAN_MODE, 0x04.toByte()),

    // Thermostat Fan Mode Supported Report:supporting -> controlling
    THERMOSTAT_FAN_MODE_SUPPORTED_REPORT(CommandClassID.THERMOSTAT_FAN_MODE, 0x05.toByte()),

    // Thermostat Fan State Get:controlling -> supporting
    THERMOSTAT_FAN_STATE_GET(CommandClassID.THERMOSTAT_FAN_STATE, 0x02.toByte()),

    // Thermostat Fan State Report:supporting -> controlling
    THERMOSTAT_FAN_STATE_REPORT(CommandClassID.THERMOSTAT_FAN_STATE, 0x03.toByte()),

    // Thermostat Mode Set:controlling -> supporting
    THERMOSTAT_MODE_SET(CommandClassID.THERMOSTAT_MODE, 0x01.toByte()),

    // Thermostat Mode Get:controlling -> supporting
    THERMOSTAT_MODE_GET(CommandClassID.THERMOSTAT_MODE, 0x02.toByte()),

    // Thermostat Mode Report:supporting -> controlling
    THERMOSTAT_MODE_REPORT(CommandClassID.THERMOSTAT_MODE, 0x03.toByte()),

    // Thermostat Mode Supported Get:controlling -> supporting
    THERMOSTAT_MODE_SUPPORTED_GET(CommandClassID.THERMOSTAT_MODE, 0x04.toByte()),

    // Thermostat Mode Supported Report:supporting -> controlling
    THERMOSTAT_MODE_SUPPORTED_REPORT(CommandClassID.THERMOSTAT_MODE, 0x05.toByte()),

    // Thermostat Operating State Get:controlling -> supporting
    THERMOSTAT_OPERATING_STATE_GET(CommandClassID.THERMOSTAT_OPERATING_STATE, 0x02.toByte()),

    // Thermostat Operating State Report:supporting -> controlling
    THERMOSTAT_OPERATING_STATE_REPORT(CommandClassID.THERMOSTAT_OPERATING_STATE, 0x03.toByte()),

    // Thermostat Operating State Logging Supported Get:controlling -> supporting
    THERMOSTAT_OPERATING_STATE_LOGGING_SUPPORTED_GET(CommandClassID.THERMOSTAT_OPERATING_STATE, 0x01.toByte()),

    // Thermostat Operating State Logging Supported Report:supporting -> controlling
    THERMOSTAT_OPERATING_LOGGING_SUPPORTED_REPORT(CommandClassID.THERMOSTAT_OPERATING_STATE, 0x04.toByte()),

    // Thermostat Operating State Logging Get:controlling -> supporting
    THERMOSTAT_OPERATING_STATE_LOGGING_GET(CommandClassID.THERMOSTAT_OPERATING_STATE, 0x05.toByte()),

    // Thermostat Operating State Logging Report:supporting -> controlling
    THERMOSTAT_OPERATING_STATE_LOGGING_REPORT(CommandClassID.THERMOSTAT_OPERATING_STATE, 0x06.toByte()),

    // Thermostat Setback Set:controlling -> supporting
    THERMOSTAT_SETBACK_SET(CommandClassID.THERMOSTAT_SETBACK, 0x01.toByte()),

    // Thermostat Setback Get:controlling -> supporting
    THERMOSTAT_SETBACK_GET(CommandClassID.THERMOSTAT_SETBACK, 0x02.toByte()),

    // Thermostat Setback Report:supporting -> controlling
    THERMOSTAT_SETBACK_REPORT(CommandClassID.THERMOSTAT_SETBACK, 0x03.toByte()),

    // Thermostat Setpoint Set:controlling -> supporting
    THERMOSTAT_SETPOINT_SET(CommandClassID.THERMOSTAT_SETPOINT, 0x01.toByte()),

    // Thermostat Setpoint Get:controlling -> supporting
    THERMOSTAT_SETPOINT_GET(CommandClassID.THERMOSTAT_SETPOINT, 0x02.toByte()),

    // Thermostat Setpoint Report:supporting -> controlling
    THERMOSTAT_SETPOINT_REPORT(CommandClassID.THERMOSTAT_SETPOINT, 0x03.toByte()),

    // Thermostat Setpoint Supported Get:controlling -> supporting
    THERMOSTAT_SETPOINT_SUPPORTED_GET(CommandClassID.THERMOSTAT_SETPOINT, 0x04.toByte()),

    // Thermostat Setpoint Supported Report:supporting -> controlling
    THERMOSTAT_SETPOINT_SUPPORTED_REPORT(CommandClassID.THERMOSTAT_SETPOINT, 0x05.toByte()),

    // Thermostat Setpoint Capabilities Get:controlling -> supporting
    THERMOSTAT_SETPOINT_CAPABILITIES_GET(CommandClassID.THERMOSTAT_SETPOINT, 0x09.toByte()),

    // Thermostat Setpoint Capabilities Report:supporting -> controlling
    THERMOSTAT_SETPOINT_CAPABILITIES_REPORT(CommandClassID.THERMOSTAT_SETPOINT, 0x0A.toByte()),

    // Time Get:controlling -> supporting
    TIME_GET(CommandClassID.TIME, 0x01.toByte()),

    // Time Report:supporting -> controlling
    TIME_REPORT(CommandClassID.TIME, 0x02.toByte()),

    // Date Get:controlling -> supporting
    DATE_GET(CommandClassID.TIME, 0x03.toByte()),

    // Date Report:supporting -> controlling
    DATE_REPORT(CommandClassID.TIME, 0x04.toByte()),

    // Time Offset Set:controlling -> supporting
    TIME_OFFSET_SET(CommandClassID.TIME, 0x05.toByte()),

    // Time Offset Get:controlling -> supporting
    TIME_OFFSET_GET(CommandClassID.TIME, 0x06.toByte()),

    // Time Offset Report:supporting -> controlling
    TIME_OFFSET_REPORT(CommandClassID.TIME, 0x07.toByte()),

    // Time Parameters Set:controlling -> supporting
    TIME_PARAMETERS_SET(CommandClassID.TIME_PARAMETERS, 0x01.toByte()),

    // Time Parameters Get:controlling -> supporting
    TIME_PARAMETERS_GET(CommandClassID.TIME_PARAMETERS, 0x02.toByte()),

    // Time Parameters Report:supporting -> controlling
    TIME_PARAMETERS_REPORT(CommandClassID.TIME_PARAMETERS, 0x03.toByte()),

    // First Segment:controlling -> supporting
    COMMAND_FIRST_FRAGMENT(CommandClassID.TRANSPORT_SERVICE, 0xC0.toByte()),

    // Segment Complete:supporting -> controlling
    COMMAND_FRAGMENT_COMPLETE(CommandClassID.TRANSPORT_SERVICE, 0xE8.toByte()),

    // Segment Request:supporting -> controlling
    COMMAND_FRAGMENT_REQUEST(CommandClassID.TRANSPORT_SERVICE, 0xC8.toByte()),

    // Segment Wait:supporting -> controlling
    COMMAND_FRAGMENT_WAIT(CommandClassID.TRANSPORT_SERVICE, 0xF0.toByte()),

    // Subsequent segment:controlling -> supporting
    COMMAND_SUBSEQUENT_FRAGMENT(CommandClassID.TRANSPORT_SERVICE, 0xE0.toByte()),

    // User Code Set:controlling -> supporting
    USER_CODE_SET(CommandClassID.USER_CODE, 0x01.toByte()),

    // User Code Get:controlling -> supporting
    USER_CODE_GET(CommandClassID.USER_CODE, 0x02.toByte()),

    // User Code Report:supporting -> controlling
    USER_CODE_REPORT(CommandClassID.USER_CODE, 0x03.toByte()),

    // Users Number Get:controlling -> supporting
    USERS_NUMBER_GET(CommandClassID.USER_CODE, 0x04.toByte()),

    // Users Number Report:supporting -> controlling
    USERS_NUMBER_REPORT(CommandClassID.USER_CODE, 0x05.toByte()),

    // User Code Capabilities Get:controlling -> supporting
    USER_CODE_CAPABILITIES_GET(CommandClassID.USER_CODE, 0x06.toByte()),

    // User Code Capabilities Report:supporting -> controlling
    USER_CODE_CAPABILITIES_REPORT(CommandClassID.USER_CODE, 0x07.toByte()),

    // User Code Keypad Mode Set:controlling -> supporting
    USER_CODE_KEYPAD_MODE_SET(CommandClassID.USER_CODE, 0x08.toByte()),

    // User Code Keypad Mode Get:controlling -> supporting
    USER_CODE_KEYPAD_MODE_GET(CommandClassID.USER_CODE, 0x09.toByte()),

    // User Code Keypad Mode Report:supporting -> controlling
    USER_CODE_KEYPAD_MODE_REPORT(CommandClassID.USER_CODE, 0x0A.toByte()),

    // Extended User Code Set:controlling -> supporting
    EXTENDED_USER_CODE_SET(CommandClassID.USER_CODE, 0x0B.toByte()),

    // Extended User Code Get:controlling -> supporting
    EXTENDED_USER_CODE_GET(CommandClassID.USER_CODE, 0x0C.toByte()),

    // Extended User Code Report:supporting -> controlling
    EXTENDED_USER_CODE_REPORT(CommandClassID.USER_CODE, 0x0D.toByte()),

    // Master Code Set:controlling -> supporting
    MASTER_CODE_SET(CommandClassID.USER_CODE, 0x0E.toByte()),

    // Master Code Get:controlling -> supporting
    MASTER_CODE_GET(CommandClassID.USER_CODE, 0x0F.toByte()),

    // Master Code Report:supporting -> controlling
    MASTER_CODE_REPORT(CommandClassID.USER_CODE, 0x10.toByte()),

    // User Code Checksum Get:controlling -> supporting
    USER_CODE_CHECKSUM_GET(CommandClassID.USER_CODE, 0x11.toByte()),

    // User Code Checksum Report:supporting -> controlling
    USER_CODE_CHECKSUM_REPORT(CommandClassID.USER_CODE, 0x12.toByte()),

    // Version Get:controlling -> supporting
    VERSION_GET(CommandClassID.VERSION, 0x11.toByte()),

    // Version Report:supporting -> controlling
    VERSION_REPORT(CommandClassID.VERSION, 0x12.toByte()),

    // Version Command Class Get:controlling -> supporting
    VERSION_COMMAND_CLASS_GET(CommandClassID.VERSION, 0x13.toByte()),

    // Version Command Class Report:supporting -> controlling
    VERSION_COMMAND_CLASS_REPORT(CommandClassID.VERSION, 0x14.toByte()),

    // Version Capabilities Get:controlling -> supporting
    VERSION_CAPABILITIES_GET(CommandClassID.VERSION, 0x15.toByte()),

    // Version Capabilities Report:supporting -> controlling
    VERSION_CAPABILITIES_REPORT(CommandClassID.VERSION, 0x16.toByte()),

    // Version Z-Wave Software Get:controlling -> supporting
    VERSION_ZWAVE_SOFTWARE_GET(CommandClassID.VERSION, 0x17.toByte()),

    // Version Z-Wave Software Report:supporting -> controlling
    VERSION_ZWAVE_SOFTWARE_REPORT(CommandClassID.VERSION, 0x18.toByte()),

    // Wake Up Interval Set:controlling -> supporting
    WAKE_UP_INTERVAL_SET(CommandClassID.WAKE_UP, 0x04.toByte()),

    // Wake Up Interval Get:controlling -> supporting
    WAKE_UP_INTERVAL_GET(CommandClassID.WAKE_UP, 0x05.toByte()),

    // Wake Up Interval Report:supporting -> controlling
    WAKE_UP_INTERVAL_REPORT(CommandClassID.WAKE_UP, 0x06.toByte()),

    // Wake Up Notification:supporting -> controlling
    WAKE_UP_NOTIFICATION(CommandClassID.WAKE_UP, 0x07.toByte()),

    // Wake Up No More Information:controlling -> supporting
    WAKE_UP_NO_MORE_INFORMATION(CommandClassID.WAKE_UP, 0x08.toByte()),

    // Wake Up Interval Capabilities Get:controlling -> supporting
    WAKE_UP_INTERVAL_CAPABILITIES_GET(CommandClassID.WAKE_UP, 0x09.toByte()),

    // Wake Up Interval Capabilities Report:supporting -> controlling
    WAKE_UP_INTERVAL_CAPABILITIES_REPORT(CommandClassID.WAKE_UP, 0x0A.toByte()),

    // Window Covering Supported Get:controlling -> supporting
    WINDOW_COVERING_SUPPORTED_GET(CommandClassID.WINDOW_COVERING, 0x01.toByte()),

    // Window Covering Supported Report:supporting -> controlling
    WINDOW_COVERING_SUPPORTED_REPORT(CommandClassID.WINDOW_COVERING, 0x02.toByte()),

    // Window Covering Get:controlling -> supporting
    WINDOW_COVERING_GET(CommandClassID.WINDOW_COVERING, 0x03.toByte()),

    // Window Covering Report:supporting -> controlling
    WINDOW_COVERING_REPORT(CommandClassID.WINDOW_COVERING, 0x04.toByte()),

    // Window Covering Set:controlling -> supporting
    WINDOW_COVERING_SET(CommandClassID.WINDOW_COVERING, 0x05.toByte()),

    // Window Covering Start Level Change:controlling -> supporting
    WINDOW_COVERING_START_LEVEL_CHANGE(CommandClassID.WINDOW_COVERING, 0x06.toByte()),

    // Window Covering Stop Level Change:controlling -> supporting
    WINDOW_COVERING_STOP_LEVEL_CHANGE(CommandClassID.WINDOW_COVERING, 0x07.toByte()),

    // Z/IP Packet:controlling -> supporting
    COMMAND_ZIP_PACKET(CommandClassID.ZIP, 0x02.toByte()),

    // Z/IP Keep Alive:controlling -> supporting
    COMMAND_ZIP_KEEP_ALIVE(CommandClassID.ZIP, 0x03.toByte()),

    // Gateway Mode Set:controlling -> supporting
    GATEWAY_MODE_SET(CommandClassID.ZIP_GATEWAY, 0x01.toByte()),

    // Gateway Mode Get:controlling -> supporting
    GATEWAY_MODE_GET(CommandClassID.ZIP_GATEWAY, 0x02.toByte()),

    // Gateway Mode Report:supporting -> controlling
    GATEWAY_MODE_REPORT(CommandClassID.ZIP_GATEWAY, 0x03.toByte()),

    // Gateway Peer Set:controlling -> supporting
    GATEWAY_PEER_SET(CommandClassID.ZIP_GATEWAY, 0x04.toByte()),

    // Gateway Peer Get:controlling -> supporting
    GATEWAY_PEER_GET(CommandClassID.ZIP_GATEWAY, 0x05.toByte()),

    // Gateway Peer Report:supporting -> controlling
    GATEWAY_PEER_REPORT(CommandClassID.ZIP_GATEWAY, 0x06.toByte()),

    // Gateway Lock Set:controlling -> supporting
    GATEWAY_LOCK_SET(CommandClassID.ZIP_GATEWAY, 0x07.toByte()),

    // Unsolicited Destination Set:controlling -> supporting
    UNSOLICITED_DESTINATION_SET(CommandClassID.ZIP_GATEWAY, 0x08.toByte()),

    // Unsolicited Destination Get:controlling -> supporting
    UNSOLICITED_DESTINATION_GET(CommandClassID.ZIP_GATEWAY, 0x09.toByte()),

    // Unsolicited Destination Report:supporting -> controlling
    UNSOLICITED_DESTINATION_REPORT(CommandClassID.ZIP_GATEWAY, 0x0A.toByte()),

    // Application Node Info Set:controlling -> supporting
    COMMAND_APPLICATION_NODE_INFO_SET(CommandClassID.ZIP_GATEWAY, 0x0B.toByte()),

    // Application Node Info Get:controlling -> supporting
    COMMAND_APPLICATION_NODE_INFO_GET(CommandClassID.ZIP_GATEWAY, 0x0C.toByte()),

    // Application Node Info Report:supporting -> controlling
    COMMAND_APPLICATION_NODE_INFO_REPORT(CommandClassID.ZIP_GATEWAY, 0x0D.toByte()),

    // Z/IP Name Set:controlling -> supporting
    ZIP_NAMING_NAME_SET(CommandClassID.ZIP_NAMING, 0x01.toByte()),

    // Z/IP Name Get:controlling -> supporting
    ZIP_NAMING_NAME_GET(CommandClassID.ZIP_NAMING, 0x02.toByte()),

    // Z/IP Name Report:supporting -> controlling
    ZIP_NAMING_NAME_REPORT(CommandClassID.ZIP_NAMING, 0x03.toByte()),

    // Z/IP Location Set:controlling -> supporting
    ZIP_NAMING_LOCATION_SET(CommandClassID.ZIP_NAMING, 0x04.toByte()),

    // Z/IP Location Get:controlling -> supporting
    ZIP_NAMING_LOCATION_GET(CommandClassID.ZIP_NAMING, 0x05.toByte()),

    // Z/IP Location Report:supporting -> controlling
    ZIP_NAMING_LOCATION_REPORT(CommandClassID.ZIP_NAMING, 0x06.toByte()),

    // Z/IP Node Advertisement:supporting -> controlling
    ZIP_NODE_ADVERTISEMENT(CommandClassID.ZIP_ND, 0x01.toByte()),

    // Z/IP Node Solicitation:controlling -> supporting
    ZIP_NODE_SOLICITATION(CommandClassID.ZIP_ND, 0x03.toByte()),

    // Z/IP Inverse Node Solicitation:controlling -> supporting
    ZIP_INV_NODE_SOLICITATION(CommandClassID.ZIP_ND, 0x04.toByte()),

    // Gateway Configuration Set:controlling -> supporting
    GATEWAY_CONFIGURATION_SET(CommandClassID.ZIP_PORTAL, 0x01.toByte()),

    // Gateway Configuration Status:supporting -> controlling
    GATEWAY_CONFIGURATION_STATUS(CommandClassID.ZIP_PORTAL, 0x02.toByte()),

    // Gateway Configuration Get:controlling -> supporting
    GATEWAY_CONFIGURATION_GET(CommandClassID.ZIP_PORTAL, 0x03.toByte()),

    // Gateway Configuration Report:supporting -> controlling
    GATEWAY_CONFIGURATION_REPORT(CommandClassID.ZIP_PORTAL, 0x04.toByte()),

    // Z-Wave Plus Info Get:controlling -> supporting
    ZWAVEPLUS_INFO_GET(CommandClassID.ZWAVEPLUS_INFO, 0x01.toByte()),

    // Z-Wave Plus Info Report:supporting -> controlling
    ZWAVEPLUS_INFO_REPORT(CommandClassID.ZWAVEPLUS_INFO, 0x02.toByte());


    companion object {
        private val ccToCommand: Map<CommandClassID, Set<CommandID>> by lazy {
            val result = mutableMapOf<CommandClassID, Set<CommandID>>()
            values().forEach {
                val commands =
                    result.getOrPut(it.commandClass, { mutableSetOf() }) as MutableSet<CommandID>
                commands.add(it)
            }
            result
        }

        fun getByCommandClass(commandClass: CommandClassID) = ccToCommand[commandClass]

        fun getByByteValue(commandClass: CommandClassID, byteValue: Byte) =
            getByCommandClass(commandClass)?.find { it.byteValue == byteValue }


    }

}