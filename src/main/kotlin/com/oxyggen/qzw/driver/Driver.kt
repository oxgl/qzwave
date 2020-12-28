package com.oxyggen.qzw.driver

import com.oxyggen.qzw.frame.Frame

interface Driver {
    val started: Boolean
    fun start(): Boolean
    fun stop()

    suspend fun getFrame(): Frame?
    fun putFrame(frame: Frame)

}