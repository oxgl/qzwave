package com.oxyggen.qzw.driver

import com.oxyggen.qzw.frame.Frame
import com.oxyggen.qzw.node.NetworkInfoGetter

interface Driver {
    val started: Boolean
    fun start(): Boolean
    fun stop()

    suspend fun getFrame(networkInfo: NetworkInfoGetter): Frame?
    fun putFrame(frame: Frame, networkInfo: NetworkInfoGetter)

}