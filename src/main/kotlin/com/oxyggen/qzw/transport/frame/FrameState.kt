package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.FunctionCallbackKey


abstract class FrameState(predecessor: Frame? = null) : Frame(predecessor) {
    override val sendTimeouts: List<Long>
        get() = SENT_TIMEOUTS_SEND_ONLY

    override fun isFunctionCallbackKeyRequired() = false

    override fun getFunctionCallbackKey(): FunctionCallbackKey? = predecessor?.getFunctionCallbackKey()

}