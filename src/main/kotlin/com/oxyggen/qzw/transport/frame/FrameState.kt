package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.types.NodeID


abstract class FrameState(network: Network, predecessor: Frame? = null) : Frame(network, predecessor) {
    override val sendTimeouts: List<Long>
        get() = SENT_TIMEOUTS_SEND_ONLY

    override fun getNodeId(): NodeID? = predecessor?.getNodeId()

    /*override fun isFunctionCallbackKeyRequired() = false

    override fun getFunctionCallbackKey(): FunctionCallbackKey? = predecessor?.getFunctionCallbackKey()*/

}