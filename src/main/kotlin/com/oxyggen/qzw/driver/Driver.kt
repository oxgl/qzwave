package com.oxyggen.qzw.driver

import com.oxyggen.qzw.frame.Frame
import com.oxyggen.qzw.function.Function

interface Driver {
    val started: Boolean
    fun start(): Boolean
    fun stop()

    suspend fun getFrame(): Frame?
    fun putFrame(frame: Frame)
    fun putFunction(function: Function)

}