package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.extensions.init
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.types.FunctionCallbackID
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.channels.Channel

class Network {
    var nodeMap: Map<NodeID, Node> = mutableMapOf()

    val node: Map<NodeID, Node> get() = nodeMap

    private val callbackKeyToFrame = mutableMapOf<NetworkCallbackKey, FrameSOF>()
    private val callbackNextKey = Channel<NetworkCallbackKey>(256)

    suspend fun initCallbackKeys() {
        // Remove all old data
        callbackNextKey.init()
        // Create new sequence
        for (cbID in FunctionCallbackID.ALL_VALID)
            callbackNextKey.send(NetworkCallbackKey(cbID))
    }

    private suspend fun registerCallbackKey(frame: FrameSOF): NetworkCallbackKey {
        val cbKey = callbackNextKey.receive()
        callbackKeyToFrame[cbKey] = frame
        return cbKey
    }

    fun getCallbackKey(frame: FrameSOF): NetworkCallbackKey? =
        callbackKeyToFrame.filterValues { it == frame }.keys.firstOrNull()

    suspend fun provideCallbackKey(frame: FrameSOF): NetworkCallbackKey =
        getCallbackKey(frame) ?: registerCallbackKey(frame)

    fun getFrameByCallbackKey(networkCallbackKey: NetworkCallbackKey): FrameSOF? =
        callbackKeyToFrame[networkCallbackKey]

    fun getNodeByCallbackKey(networkCallbackKey: NetworkCallbackKey, callerFrame: FrameSOF? = null): Node? {
        val foundFrame = getFrameByCallbackKey(networkCallbackKey)
        return if (foundFrame != callerFrame)
            foundFrame?.getNode()
        else
            null
    }

    suspend fun deregisterCallbackKey(cbKey: NetworkCallbackKey): FrameSOF? {
        // Get the frame from callback map
        val result = callbackKeyToFrame.remove(cbKey)
        // If frame found, remove entry and also put back callback key into
        if (result != null)
            callbackNextKey.send(cbKey)
        return result
    }
}