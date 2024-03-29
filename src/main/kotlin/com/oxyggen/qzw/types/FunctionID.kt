package com.oxyggen.qzw.types

enum class FunctionID(override val byteValue: Byte) : TypeToByte {
    APPLICATION_COMMAND_HANDLER(0x04.toByte()),
    APPLICATION_COMMAND_HANDLER_BRIDGE(0xA8.toByte()),
    APPLICATION_SECURITY_EVENT(0x9D.toByte()),
    APPLICATION_SLAVE_COMMAND_HANDLER(0xA1.toByte()),
    AUTO_PROGRAMMING(0x27.toByte()),
    CLEAR_TX_TIMERS(0x37.toByte()),
    CLOCK_CMP(0x32.toByte()),
    CLOCK_GET(0x31.toByte()),
    CLOCK_SET(0x30.toByte()),
    DEBUG_OUTPUT(0x26.toByte()),
    GET_ROUTING_TABLE_LINE(0x80.toByte()),
    GET_TX_COUNTER(0x81.toByte()),
    GET_TX_TIMERS(0x38.toByte()),
    IO_PORT(0xE6.toByte()),
    IO_PORT_STATUS(0xE5.toByte()),
    LOCK_ROUTE_RESPONSE(0x90.toByte()),
    MEMORY_GET_BUFFER(0x23.toByte()),
    MEMORY_GET_BYTE(0x21.toByte()),
    MEMORY_GET_ID(0x20.toByte()),
    MEMORY_PUT_BUFFER(0x24.toByte()),
    MEMORY_PUT_BYTE(0x22.toByte()),
    NVM_BACKUP_RESTORE(0x2E.toByte()),
    NVM_EXT_READ_LONG_BUFFER(0x2A.toByte()),
    NVM_EXT_READ_LONG_BYTE(0x2C.toByte()),
    NVM_EXT_WRITE_LONG_BUFFER(0x2B.toByte()),
    NVM_EXT_WRITE_LONG_BYTE(0x2D.toByte()),
    NVM_GET_ID(0x29.toByte()),
    NVR_GET_VALUE(0x28.toByte()),
    PROMISCUOUS_APPLICATION_COMMAND_HANDLER(0xD1.toByte()),
    PROPRIETARY_0(0xF0.toByte()),
    PROPRIETARY_1(0xF1.toByte()),
    PROPRIETARY_2(0xF2.toByte()),
    PROPRIETARY_3(0xF3.toByte()),
    PROPRIETARY_4(0xF4.toByte()),
    PROPRIETARY_5(0xF5.toByte()),
    PROPRIETARY_6(0xF6.toByte()),
    PROPRIETARY_7(0xF7.toByte()),
    PROPRIETARY_8(0xF8.toByte()),
    PROPRIETARY_9(0xF9.toByte()),
    PROPRIETARY_A(0xFA.toByte()),
    PROPRIETARY_B(0xFB.toByte()),
    PROPRIETARY_C(0xFC.toByte()),
    PROPRIETARY_D(0xFD.toByte()),
    PROPRIETARY_E(0xFE.toByte()),
    PWR_CLK_PD(0xB1.toByte()),
    PWR_CLK_PUP(0xB2.toByte()),
    PWR_SELECT_CLK(0xB3.toByte()),
    PWR_SETSTOPMODE(0xB0.toByte()),
    RESET_TX_COUNTER(0x82.toByte()),
    RTC_TIMER_CALL(0x36.toByte()),
    RTC_TIMER_CREATE(0x33.toByte()),
    RTC_TIMER_DELETE(0x35.toByte()),
    RTC_TIMER_READ(0x34.toByte()),
    SERIAL_API_APPL_NODE_INFORMATION(0x03.toByte()),
    SERIAL_API_APPL_NODE_INFORMATION_CMD_CLASSES(0x0C.toByte()),
    SERIAL_API_APPL_SLAVE_NODE_INFORMATION(0xA0.toByte()),
    SERIAL_API_EXT(0x98.toByte()),
    SERIAL_API_GET_APPL_HOST_MEMORY_OFFSET(0x25.toByte()),
    SERIAL_API_GET_CAPABILITIES(0x07.toByte()),
    SERIAL_API_GET_INIT_DATA(0x02.toByte()),
    SERIAL_API_POWER_MANAGEMENT(0xEE.toByte()),
    SERIAL_API_READY(0xEF.toByte()),
    SERIAL_API_SET_TIMEOUTS(0x06.toByte()),
    SERIAL_API_SETUP(0x0B.toByte()),
    SERIAL_API_SOFT_RESET(0x08.toByte()),
    SERIAL_API_STARTED(0x0A.toByte()),
    SERIAL_API_TEST(0x95.toByte()),
    STORE_HOMEID(0x84.toByte()),
    STORE_NODEINFO(0x83.toByte()),
    TIMER_CALL(0x73.toByte()),
    TIMER_CANCEL(0x72.toByte()),
    TIMER_RESTART(0x71.toByte()),
    TIMER_START(0x70.toByte()),
    UNKNOWN(0xFF.toByte()),
    ZW_ADD_NODE_TO_NETWORK(0x4A.toByte()),
    ZW_AES_ECB(0x67.toByte()),

    //ZW_APPLICATION_CONTROLLER_UPDATE(0x49.toByte()),
    ZW_APPLICATION_UPDATE(0x49.toByte()),
    ZW_ARE_NODES_NEIGHBOURS(0xBC.toByte()),
    ZW_ASSIGN_PRIORITY_RETURN_ROUTE(0x4F.toByte()),
    ZW_ASSIGN_PRIORITY_SUC_RETURN_ROUTE(0x58.toByte()),
    ZW_ASSIGN_RETURN_ROUTE(0x46.toByte()),
    ZW_ASSIGN_SUC_RETURN_ROUTE(0x51.toByte()),
    ZW_CLEAR_NETWORK_STATS(0x39.toByte()),
    ZW_CONTROLLER_CHANGE(0x4D.toByte()),
    ZW_CREATE_NEW_PRIMARY(0x4C.toByte()),
    ZW_DELETE_RETURN_ROUTE(0x47.toByte()),
    ZW_DELETE_SUC_RETURN_ROUTE(0x55.toByte()),
    ZW_ENABLE_SUC(0x52.toByte()),
    ZW_EXPLORE_REQUEST_EXCLUSION(0x5F.toByte()),
    ZW_EXPLORE_REQUEST_INCLUSION(0x5E.toByte()),
    ZW_FIRMWARE_UPDATE_NVM(0x78.toByte()),
    ZW_GET_BACKGROUND_RSSI(0x3B.toByte()),
    ZW_GET_CONTROLLER_CAPABILITIES(0x05.toByte()),
    ZW_GET_LAST_WORKING_ROUTE(0x92.toByte()),
    ZW_GET_NEIGHBOR_COUNT(0xBB.toByte()),
    ZW_GET_NETWORK_STATS(0x3A.toByte()),
    ZW_GET_NODE_PROTOCOL_INFO(0x41.toByte()),
    ZW_GET_PRIORITY_ROUTE(0x92.toByte()),
    ZW_GET_PROTOCOL_STATUS(0xBF.toByte()),
    ZW_GET_PROTOCOL_VERSION(0x09.toByte()),
    ZW_GET_RANDOM(0x1C.toByte()),
    ZW_GET_ROUTING_MAX(0xD5.toByte()),
    ZW_GET_SUC_NODE_ID(0x56.toByte()),
    ZW_GET_VERSION(0x15.toByte()),
    ZW_GET_VIRTUAL_NODES(0xA5.toByte()),
    ZW_INT_EXT_LEVEL_SET(0xB9.toByte()),
    ZW_IS_FAILED_NODE_ID(0x62.toByte()),
    ZW_IS_NODE_WITHIN_DIRECT_RANGE(0x5D.toByte()),
    ZW_IS_PRIMARY_CTRL(0x66.toByte()),
    ZW_IS_VIRTUAL_NODE(0xA6.toByte()),
    ZW_IS_WUT_KICKED(0xB5.toByte()),
    ZW_NETWORK_MANAGEMENT_SET_MAX_INCLUSION_REQUEST_INTERVALS(0xD6.toByte()),
    ZW_NEW_CONTROLLER(0x43.toByte()),
    ZW_NUNIT_CMD(0xE0.toByte()),
    ZW_NUNIT_END(0xE4.toByte()),
    ZW_NUNIT_INIT(0xE1.toByte()),
    ZW_NUNIT_LIST(0xE2.toByte()),
    ZW_NUNIT_RUN(0xE3.toByte()),
    ZW_NVR_GET_APP_VALUE(0x2F.toByte()),
    ZW_RANDOM(0x1D.toByte()),
    ZW_REDISCOVERY_NEEDED(0x59.toByte()),
    ZW_REMOVE_FAILED_NODE_ID(0x61.toByte()),
    ZW_REMOVE_NODE_FROM_NETWORK(0x4B.toByte()),
    ZW_REMOVE_NODE_ID_FROM_NETWORK(0x3F.toByte()),
    ZW_REPLACE_FAILED_NODE(0x63.toByte()),
    ZW_REPLICATION_COMMAND_COMPLETE(0x44.toByte()),
    ZW_REPLICATION_SEND_DATA(0x45.toByte()),
    ZW_REQUEST_NETWORK_UPDATE(0x53.toByte()),
    ZW_REQUEST_NEW_ROUTE_DESTINATIONS(0x5C.toByte()),
    ZW_REQUEST_NODE_INFO(0x60.toByte()),
    ZW_REQUEST_NODE_NEIGHBOR_UPDATE(0x48.toByte()),
    ZW_REQUEST_NODE_NEIGHBOR_UPDATE_OPTION(0x5A.toByte()),
    ZW_RESERVED_FN(0x4E.toByte()),
    ZW_RESERVED_SD(0x19.toByte()),
    ZW_RESERVED_SDM(0x1A.toByte()),
    ZW_RESERVED_SRI(0x1B.toByte()),
    ZW_RESERVED_SSD(0xA7.toByte()),
    ZW_RF_POWER_LEVEL_GET(0xBA.toByte()),
    ZW_RF_POWER_LEVEL_REDISCOVERY_SET(0x1E.toByte()),
    ZW_RF_POWER_LEVEL_SET(0x17.toByte()),
    ZW_SECURITY_SETUP(0x9C.toByte()),
    ZW_SEND_DATA(0x13.toByte()),
    ZW_SEND_DATA_ABORT(0x16.toByte()),
    ZW_SEND_DATA_BRIDGE(0xA9.toByte()),
    ZW_SEND_DATA_EX(0x0E.toByte()),
    ZW_SEND_DATA_META(0x18.toByte()),
    ZW_SEND_DATA_META_BRIDGE(0xAA.toByte()),
    ZW_SEND_DATA_MULTI(0x14.toByte()),
    ZW_SEND_DATA_MULTI_BRIDGE(0xAB.toByte()),
    ZW_SEND_DATA_MULTI_EX(0x0F.toByte()),
    ZW_SEND_DATA_ROUTE_DEMO(0x91.toByte()),
    ZW_SEND_NODE_INFORMATION(0x12.toByte()),
    ZW_SEND_SLAVE_DATA(0xA3.toByte()),
    ZW_SEND_SLAVE_NODE_INFORMATION(0xA2.toByte()),
    ZW_SEND_SUC_ID(0x57.toByte()),
    ZW_SEND_TEST_FRAME(0xBE.toByte()),
    ZW_SET_DEFAULT(0x42.toByte()),
    ZW_SET_EXT_INT_LEVEL(0xB9.toByte()),
    ZW_SET_LAST_WORKING_ROUTE(0x93.toByte()),
    ZW_SET_LEARN_MODE(0x50.toByte()),
    ZW_SET_LEARN_NODE_STATE(0x40.toByte()),
    ZW_SET_LISTEN_BEFORE_TALK_THRESHOLD(0x3C.toByte()),
    ZW_SET_PRIORITY_ROUTE(0x93.toByte()),
    ZW_SET_PROMISCUOUS_MODE(0xD0.toByte()),
    ZW_SET_RF_RECEIVE_MODE(0x10.toByte()),
    ZW_SET_ROUTING_INFO(0x1B.toByte()),
    ZW_SET_ROUTING_MAX(0xD4.toByte()),
    ZW_SET_ROUTING_MAX_6_00(0x65.toByte()),
    ZW_SET_SLAVE_LEARN_MODE(0xA4.toByte()),
    ZW_SET_SLEEP_MODE(0x11.toByte()),
    ZW_SET_SUC_NODE_ID(0x54.toByte()),
    ZW_SET_WUT_TIMEOUT(0xB4.toByte()),
    ZW_SUPPORT9600_ONLY(0x5B.toByte()),
    ZW_TYPE_LIBRARY(0xBD.toByte()),
    ZW_WATCHDOG_DISABLE(0xB7.toByte()),
    ZW_WATCHDOG_ENABLE(0xB6.toByte()),
    ZW_WATCHDOG_KICK(0xB8.toByte()),
    ZW_WATCHDOG_START(0xD2.toByte()),
    ZW_WATCHDOG_STOP(0xD3.toByte());

    companion object : ByteToType<FunctionID> {
        override fun getByByteValue(byteValue: Byte) =
            values().firstOrNull { it.byteValue == byteValue }
    }
}