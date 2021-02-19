package com.oxyggen.qzw.engine.driver

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.transport.frame.Frame

interface Driver {
    val started: Boolean
    fun start(): Boolean
    fun stop()

    fun dataAvailable(): Int
    suspend fun getFrame(network: Network): Frame?
    suspend fun putFrame(frame: Frame)

}