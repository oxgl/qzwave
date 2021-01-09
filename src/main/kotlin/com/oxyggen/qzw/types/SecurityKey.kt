package com.oxyggen.qzw.types

enum class SecurityKey(override val byteValue: Byte) :TypeToByte {
    NONE(0x00.toByte()),
    S2_UNAUTHENTICATED(0x01.toByte()),
    S2_AUTHENTICATED(0x02.toByte()),
    S2_ACCESS(0x03.toByte()),
    S0(0x04.toByte());

    companion object : ByteToType<SecurityKey> {
        override fun getByByteValue(byteValue: Byte): SecurityKey? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}