package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

class NodeScheduler(
    val parent: NetworkScheduler,
    val nodeID: NodeID,
    fromSW: ReceiveChannel<Frame> = Channel(),      // From software component (in)
    toSW: SendChannel<Frame> = Channel(),           // To software component (out)
    fromZW: ReceiveChannel<Frame> = Channel(),      // From ZWave driver (received)
    toZW: SendChannel<Frame> = Channel()            // To ZWave driver (send))
) : FrameScheduler(fromSW, toSW, fromZW, toZW)