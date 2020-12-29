@file:Suppress("ReplaceCallWithBinaryOperator")

package com.oxyggen.qzw.types

import kotlin.experimental.and

data class ReceiveStatus(
    val routedBusy: Boolean,         // A response route is locked by the application
    val lowPower: Boolean,           // Received at low output power level
    val castType: CastType,         // Received frame cast
    val explore: Boolean,            // Received an explore frame
    val foreignFrame: Boolean,       // The received frame is not addressed to this node (Only valid in promiscuous mode)
    val foreignHomeId: Boolean       // The received frame is received from a foreign HomeID. Only Controllers in Smart Start AddNode mode can receive this status.
) {
    enum class CastType(val byteValue: Byte) {
        SINGLE(0x00),
        BROAD(0x04),
        MULTI(0x08)
    }

    val byteValue: Byte
        get() = (
                    if (routedBusy) 0x01 else 0x00 +
                    if (lowPower) 0x02 else 0x00 +
                    castType.byteValue +
                    if (explore) 0x10 else 0x00 +
                    if (foreignFrame) 0x40 else 0x00 +
                    if (foreignHomeId) 0x80 else 0x00
                ).toByte()


    companion object {
        fun getByByteValue(byteValue: Byte): ReceiveStatus {
            // A response route is locked by the application
            // RECEIVE_STATUS_ROUTED_BUSY          xxxxxxx1
            val routedBusy = byteValue.and(0x01) == 0x01.toByte()

            // Received at low output power level
            // RECEIVE_STATUS_LOW_POWER            xxxxxx1x
            val lowPower = byteValue.and(0x02) == 0x02.toByte()

            // Cast type
            val castType = when (byteValue.and(0x0C)) {
                // Received a broadcast frame
                // RECEIVE_STATUS_TYPE_BROAD           xxxx01xx
                0x04.toByte() -> CastType.BROAD
                // Received a multicast frame
                // RECEIVE_STATUS_TYPE_MULTI           xxxx10xx
                0x08.toByte() -> CastType.MULTI
                // Received a single cast frame
                // RECEIVE_STATUS_TYPE_SINGLE          xxxx00xx
                else -> CastType.SINGLE
            }

            // Received an explore frame
            // RECEIVE_STATUS_TYPE_EXPLORE         xx10xxxx
            val explore = byteValue.and(0x10) == 0x10.toByte()

            // The received frame is not addressed to this node
            // (Only valid in promiscuous mode)
            // RECEIVE_STATUS_FOREIGN_FRAME        x1xxxxxx
            val foreignFrame = byteValue.and(0x40) == 0x40.toByte()

            // The received frame is received from a foreign HomeID.
            // Only Controllers in Smart Start AddNode mode
            // can receive this status.
            val foreignHomeId = byteValue.and(0x80.toByte()) == 0x80.toByte()

            // Finally create object
            return ReceiveStatus(routedBusy, lowPower, castType, explore, foreignFrame, foreignHomeId)
        }
    }
}