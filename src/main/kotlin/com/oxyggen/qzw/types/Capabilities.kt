package com.oxyggen.qzw.types

import com.oxyggen.qzw.extensions.build
import com.oxyggen.qzw.extensions.get

data class Capabilities(
    val slaveApi: Boolean,               // Controller API / Slave API
    val timerSupported: Boolean,        // Timer functions not supported / Timer functions supported.
    val secondaryController: Boolean,    // Primary Controller / Secondary Controller
    val sisController: Boolean           // Not SIS / Controller is SIS
) {

    val byteValue: Byte
        get() = Byte.build(slaveApi, timerSupported, secondaryController, sisController)

    companion object {
        fun getByByteValue(byteValue: Byte): Capabilities {
            val slaveApi = byteValue[0]

            val timerSupported = byteValue[1]

            val secondaryController = byteValue[2]

            val sisController = byteValue[3]

            return Capabilities(slaveApi, timerSupported, secondaryController, sisController)
        }

    }

    override fun toString(): String =
        if (slaveApi) "Slave API, " else "Controller API, " +
        if (timerSupported) "Timer supported, " else "Timer not supported, " +
        if (secondaryController) "Secondary controller, " else "Primary controller, " +
        if (sisController) "SIS controller" else "Not SIS controller"

}