package com.oxyggen.qzw.engine.driver

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.engine.network.NetworkInfoGetter

interface Driver {
    val started: Boolean
    fun start(): Boolean
    fun stop()

    fun dataAvailable():Int
    suspend fun getFrame(networkInfo: NetworkInfoGetter): Frame?
    fun putFrame(frame: Frame, networkInfo: NetworkInfoGetter)

}