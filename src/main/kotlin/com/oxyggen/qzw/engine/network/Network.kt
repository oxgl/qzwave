package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.extensions.init
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.channels.Channel

class Network {
    private var nodeMap: MutableMap<NodeID, Node> = mutableMapOf()
    private val callbackKeyToFrame = mutableMapOf<NetworkCallbackKey, FrameSOF>()
    private val callbackNextKey = Channel<NetworkCallbackKey>(256)

    suspend fun initCallbackKeys() {
        // Remove all old data
        callbackNextKey.init()
        // Create new sequence
        for (cbID in NetworkCallbackID.ALL_VALID)
            callbackNextKey.send(NetworkCallbackKey(cbID))
    }

    private suspend fun registerCallbackKey(frame: FrameSOF): NetworkCallbackKey {
        val cbKey = callbackNextKey.receive()
        callbackKeyToFrame[cbKey] = frame
        return cbKey
    }

    fun getCallbackKey(frame: FrameSOF): NetworkCallbackKey? =
        callbackKeyToFrame.filterValues { it == frame }.keys.firstOrNull()

    fun getNode(nodeID: NodeID): Node = nodeMap.getOrPut(nodeID, { Node(nodeID) })

    suspend fun provideCallbackKey(frame: FrameSOF): NetworkCallbackKey =
        getCallbackKey(frame) ?: registerCallbackKey(frame)

    fun dequeueByCallbackKey(networkCallbackKey: NetworkCallbackKey): FrameSOF? =
        callbackKeyToFrame.remove(networkCallbackKey)

    suspend fun deregisterCallbackKey(cbKey: NetworkCallbackKey): FrameSOF? {
        // Get the frame from callback map
        val result = callbackKeyToFrame.remove(cbKey)
        // If frame found, remove entry and also put back callback key into
        if (result != null)
            callbackNextKey.send(cbKey)
        return result
    }
}