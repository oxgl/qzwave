package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.FunctionCallbackID

@OptIn(ExperimentalUnsignedTypes::class)
data class NetworkCallbackKey(val functionCallbackID: FunctionCallbackID) {
    fun next(): NetworkCallbackKey = NetworkCallbackKey(functionCallbackID + 1)
}
