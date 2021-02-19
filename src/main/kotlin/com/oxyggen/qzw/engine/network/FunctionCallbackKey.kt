package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.FunctionCallbackID

@OptIn(ExperimentalUnsignedTypes::class)
data class FunctionCallbackKey(val functionCallbackID: FunctionCallbackID) {
    fun next(): FunctionCallbackKey = FunctionCallbackKey(functionCallbackID + 1)
}
