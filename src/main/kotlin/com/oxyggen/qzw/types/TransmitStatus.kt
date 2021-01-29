package com.oxyggen.qzw.types

enum class TransmitStatus(override val byteValue: Byte) : TypeToByte {
    COMPLETE_OK(0x00),
    COMPLETE_NO_ACK(0x01),      /* retransmission error */
    COMPLETE_FAIL(0x02),        /* transmit error */
    ROUTING_NOT_IDLE(0x03),     /* transmit error */
    COMPLETE_NOROUTE(0x04),     /* no route found in assignroute */
    COMPLETE_VERIFIED(0x05);    /* Verified delivery */

    companion object : ByteToType<TransmitStatus> {
        override fun getByByteValue(byteValue: Byte): TransmitStatus? =
            values().firstOrNull {
                it.byteValue == byteValue
            }
    }
}