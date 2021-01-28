package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.NetworkInfoGetter

abstract class DeserializableObjectContext(val networkInfo: NetworkInfoGetter) {
    abstract fun getSignatureByte(): Byte
}