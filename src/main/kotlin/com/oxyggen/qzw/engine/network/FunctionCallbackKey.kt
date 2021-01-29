package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.FunctionCallbackID

@OptIn(ExperimentalUnsignedTypes::class)
data class FunctionCallbackKey(val functionCallbackID: FunctionCallbackID) {
    fun next(): FunctionCallbackKey =
        if (functionCallbackID < UByte.MAX_VALUE) FunctionCallbackKey((functionCallbackID.toInt() + 1).toUByte())
        else FunctionCallbackKey(1u)
}
