@file:Suppress("ReplaceCallWithBinaryOperator")

package com.oxyggen.qzw.types

import com.oxyggen.qzw.extensions.get
import com.oxyggen.qzw.extensions.withBit
import com.oxyggen.qzw.function.ByteToEnum
import kotlin.experimental.and

data class ReceiveStatus(
    val routedBusy: Boolean,         // A response route is locked by the application
    val lowPower: Boolean,           // Received at low output power level
    val castType: CastType,          // Received frame cast
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
        get() = castType.byteValue
            .withBit(0, routedBusy)
            .withBit(1, lowPower)
                // bits 2, 3 are castType bits
            .withBit(4, explore)
            .withBit(6, foreignFrame)
            .withBit(7, foreignHomeId)

    companion object {
        fun getByByteValue(byteValue: Byte): ReceiveStatus {
            // Prepare Cast type
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

            // Finally create object
            return ReceiveStatus(
                // A response route is locked by the application
                // RECEIVE_STATUS_ROUTED_BUSY          xxxxxxx1
                routedBusy = byteValue[0],
                // Received at low output power level
                // RECEIVE_STATUS_LOW_POWER            xxxxxx1x
                lowPower = byteValue[1],
                // Cast type
                castType = castType,
                // Received an explore frame
                // RECEIVE_STATUS_TYPE_EXPLORE         xxx1xxxx
                explore = byteValue[4],
                // The received frame is not addressed to this node
                // (Only valid in promiscuous mode)
                // RECEIVE_STATUS_FOREIGN_FRAME        x1xxxxxx
                foreignFrame = byteValue[6],
                // The received frame is received from a foreign HomeID.
                // Only Controllers in Smart Start AddNode mode
                // can receive this status.
                foreignHomeId = byteValue[7]
            )
        }
    }
}