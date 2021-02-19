package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.extensions.init
import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.frame.FrameACK
import com.oxyggen.qzw.transport.frame.FrameState
import com.oxyggen.qzw.types.FunctionCallbackID
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import java.io.IOException

@OptIn(ExperimentalUnsignedTypes::class, ExperimentalCoroutinesApi::class)
class NetworkInfo {
    //}: NetworkInfoGetter {
    var nodeMap: Map<NodeID, Node> = mutableMapOf()

    val node: Map<NodeID, Node> get() = nodeMap

    // Free callback ID channel
    private val callbackMap = mutableMapOf<FunctionCallbackKey, Frame>()
    private val callbackNextKey = Channel<FunctionCallbackKey>(256)
    private var functionCallbackKey: FunctionCallbackKey? = null

    suspend fun initCallbacks() {
        // Remove all old data
        callbackNextKey.init()
        // Create new sequence
        for (cbID in FunctionCallbackID.ALL_VALID)
            callbackNextKey.send(FunctionCallbackKey(cbID))
    }

    suspend fun handleFrameEnqueue(frame: Frame, action: suspend (frame: Frame) -> Frame): Frame {
        functionCallbackKey = enqueueCallbackKey(frame)
        try {
            val resultFrame = action(frame)

            if (frame is FrameState && frame !is FrameACK)
                functionCallbackKey?.let { dequeueCallbackKey(it) }

            return resultFrame
        } catch (e: Exception) {
            // Exception occurred, remove callbackKey
            functionCallbackKey?.let { dequeueCallbackKey(it) }
            throw e
        } finally {
            // Status result not received, remove callbackID
            functionCallbackKey = null
        }
    }

    fun enqueueCallbackKey(frame: Frame): FunctionCallbackKey? = null

    suspend fun dequeueCallbackKey(functionCallbackKey: FunctionCallbackKey): Frame? {
        // Get the frame from callback map
        val result = callbackMap.remove(functionCallbackKey)
        // If frame found, remove entry and also put back callback key into
        if (result != null)
            callbackNextKey.send(functionCallbackKey)
        return result
    }

    fun isFrameWaitingForResult(frame: Frame): Boolean {
        var currentFrame: Frame? = frame
        while (currentFrame != null) {
            if (callbackMap.containsValue(frame))
                return true
            currentFrame = currentFrame.predecessor
        }
        return false
    }

    fun getCurrentCallbackKey(): FunctionCallbackKey = functionCallbackKey
        ?: throw IOException("Invalid usage! Callback not requested by frame, but it tries to get one!")

}