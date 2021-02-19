package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.extensions.init
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.types.FunctionCallbackID
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.channels.Channel

class Network {
    var nodeMap: Map<NodeID, Node> = mutableMapOf()

    val node: Map<NodeID, Node> get() = nodeMap

    private val callbackKeyToFrame = mutableMapOf<FunctionCallbackKey, FrameSOF>()
    private val callbackNextKey = Channel<FunctionCallbackKey>(256)

    suspend fun initCallbackKeys() {
        // Remove all old data
        callbackNextKey.init()
        // Create new sequence
        for (cbID in FunctionCallbackID.ALL_VALID)
            callbackNextKey.send(FunctionCallbackKey(cbID))
    }

    private suspend fun registerCallbackKey(frame: FrameSOF): FunctionCallbackKey {
        val cbKey = callbackNextKey.receive()
        callbackKeyToFrame[cbKey] = frame
        return cbKey
    }

    fun getCallbackKey(frame: FrameSOF): FunctionCallbackKey? =
        callbackKeyToFrame.filterValues { it == frame }.keys.firstOrNull()

    suspend fun provideCallbackKey(frame: FrameSOF): FunctionCallbackKey =
        getCallbackKey(frame) ?: registerCallbackKey(frame)

    suspend fun deregisterCallbackKey(cbKey: FunctionCallbackKey): FrameSOF? {
        // Get the frame from callback map
        val result = callbackKeyToFrame.remove(cbKey)
        // If frame found, remove entry and also put back callback key into
        if (result != null)
            callbackNextKey.send(cbKey)
        return result
    }
}