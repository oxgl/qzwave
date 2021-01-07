package com.oxyggen.qzw.types

import com.oxyggen.qzw.extensions.build
import com.oxyggen.qzw.extensions.get

class TransmitOptions(
    val requestAcknowledge: Boolean = true,
    val lowPower: Boolean = false,
    val autoRoute: Boolean = true,
    val noRoute: Boolean = false,
    val explore: Boolean = false
) {
    companion object : ByteToClass<TransmitOptions> {
        override fun getByByteValue(byteValue: Byte): TransmitOptions =
            TransmitOptions(
                requestAcknowledge = byteValue[0],
                lowPower = byteValue[1],
                autoRoute = byteValue[2],
                noRoute = byteValue[4],
                explore = byteValue[5]
            )
    }

    val byteValue: Byte
        get() = Byte.build(explore, noRoute, false, autoRoute, lowPower, requestAcknowledge)

}