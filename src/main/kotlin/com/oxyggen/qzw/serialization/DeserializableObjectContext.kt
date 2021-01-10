package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.node.NetworkInfoGetter

abstract class DeserializableObjectContext(val networkInfo: NetworkInfoGetter) {
    abstract fun getSignatureByte(): Byte
}