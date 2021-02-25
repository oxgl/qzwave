package com.oxyggen.qzw.engine.network

@OptIn(ExperimentalUnsignedTypes::class)
data class NetworkCallbackKey(val networkCallbackID: NetworkCallbackID) {
    fun next(): NetworkCallbackKey = NetworkCallbackKey(networkCallbackID + 1)
}
