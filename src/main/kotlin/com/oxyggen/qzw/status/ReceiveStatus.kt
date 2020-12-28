package com.oxyggen.qzw.status

import kotlin.experimental.and

class ReceiveStatus(val byteValue: Byte = 0x00) {
    // A response route is locked by the application
    // RECEIVE_STATUS_ROUTED_BUSY          xxxxxxx1
    val routedBusy = byteValue.and(0x01).equals(0x01)

    // Received at low output power level
    // RECEIVE_STATUS_LOW_POWER            xxxxxx1x
    val lowPower = byteValue.and(0x02).equals(0x02)

    // Received a single cast frame
    // RECEIVE_STATUS_TYPE_SINGLE          xxxx00xx
    val singlecast = byteValue.and(0x0C).equals(0x00)

    // Received a broadcast frame
    // RECEIVE_STATUS_TYPE_BROAD           xxxx01xx
    val broadcast = byteValue.and(0x0C).equals(0x04)

    // Received a multicast frame
    // RECEIVE_STATUS_TYPE_MULTI           xxxx10xx
    val multicast = byteValue.and(0x0C).equals(0x08)

    // Received an explore frame
    // RECEIVE_STATUS_TYPE_EXPLORE         xx10xxxx
    val explore = byteValue.and(0x10).equals(0x10)

    // The received frame is not addressed to this node
    // (Only valid in promiscuous mode)
    // RECEIVE_STATUS_FOREIGN_FRAME        x1xxxxxx
    val foreignFrame = byteValue.and(0x40).equals(0x40)

    // The received frame is received from a foreign HomeID.
    // Only Controllers in Smart Start AddNode mode
    // can receive this status.
    val foreignHomeId = byteValue.and(-0x80).equals(-0x80)
}