package com.oxyggen.qzw.types

import com.oxyggen.qzw.extensions.build
import com.oxyggen.qzw.extensions.get

data class Capabilities(
    val slaveApi: Boolean,               // Controller API / Slave API
    val timerSupported: Boolean,         // Timer functions not supported / Timer functions supported.
    val secondaryController: Boolean,    // Primary Controller / Secondary Controller
    val sisController: Boolean           // Not SIS / Controller is SIS
) : TypeToByte {

    override val byteValue: Byte
        get() = Byte.build(sisController, secondaryController, timerSupported, slaveApi)

    companion object : ByteToType<Capabilities> {
        override fun getByByteValue(byteValue: Byte): Capabilities =
            Capabilities(
                slaveApi = byteValue[0],
                timerSupported = byteValue[1],
                secondaryController = byteValue[2],
                sisController = byteValue[3]
            )
    }

    override fun toString(): String =
        if (slaveApi) "Slave API, " else "Controller API, " +
                if (timerSupported) "Timer supported, " else "Timer not supported, " +
                        if (secondaryController) "Secondary controller, " else "Primary controller, " +
                                if (sisController) "SIS controller" else "Not SIS controller"

}