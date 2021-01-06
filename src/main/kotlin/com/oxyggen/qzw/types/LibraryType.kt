package com.oxyggen.qzw.types

import com.oxyggen.qzw.function.ByteToEnum

enum class LibraryType(val byteValue: Byte) {
    CONTROLLER_STATIC(0x01),
    CONTROLLER(0x02),
    SLAVE_ENHANCED(0x03),
    SLAVE(0x04),
    INSTALLER(0x05),
    SLAVE_ROUTING(0x06),
    CONTROLLER_BRIDGE(0x07),
    DUT(0x08),
    AVREMOTE(0x0A),
    AVDEVICE(0x0B);

    companion object :ByteToEnum<LibraryType> {
        override fun getByByteValue(byteValue: Byte): LibraryType? =
            values().firstOrNull { it.byteValue == byteValue }
    }
}