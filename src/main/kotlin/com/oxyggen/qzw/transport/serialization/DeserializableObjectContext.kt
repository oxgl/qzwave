package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.Network

abstract class DeserializableObjectContext(val network: Network) {
    abstract fun getSignatureByte(): Byte
}