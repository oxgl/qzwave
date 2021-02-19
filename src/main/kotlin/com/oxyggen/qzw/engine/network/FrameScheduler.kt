package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.transport.frame.Frame
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

abstract class FrameScheduler(
    val fromSW: ReceiveChannel<Frame> = Channel(),      // From software component (in)
    val toSW: SendChannel<Frame> = Channel(),           // To software component (out)
    val fromZW: ReceiveChannel<Frame> = Channel(),      // From ZWave driver (received)
    val toZW: SendChannel<Frame> = Channel()            // To ZWave driver (send))
)