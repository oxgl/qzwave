package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.types.ByteToType
import com.oxyggen.qzw.types.TypeToByte

@OptIn(ExperimentalUnsignedTypes::class)
data class NetworkCallbackID(override val byteValue: Byte) : TypeToByte, Comparable<NetworkCallbackID> {

    constructor(i: Int) : this(i.toByte())

    companion object : ByteToType<NetworkCallbackID> {
        val MAX_VALUE = NetworkCallbackID(255)
        val MIN_VALUE = NetworkCallbackID(1)
        val ALL_VALID = NetworkCallbackIDIterator(MIN_VALUE, MAX_VALUE) as Iterator<NetworkCallbackID>
        override fun getByByteValue(byteValue: Byte): NetworkCallbackID = NetworkCallbackID(byteValue)
    }

    private class NetworkCallbackIDIterator(first: NetworkCallbackID, val last: NetworkCallbackID) :
        Iterator<NetworkCallbackID> {

        private var current = first

        override fun hasNext(): Boolean = current < last

        override fun next(): NetworkCallbackID = if (hasNext()) ++current else last
    }

    override fun compareTo(other: NetworkCallbackID): Int =
        this.byteValue.toUByte().compareTo(other.byteValue.toUByte())

    operator fun inc(): NetworkCallbackID =
        if (this < MAX_VALUE) NetworkCallbackID(byteValue.toUByte().toInt() + 1) else NetworkCallbackID(0)

    operator fun dec(): NetworkCallbackID =
        if (this > MIN_VALUE) NetworkCallbackID(byteValue.toUByte().toInt() - 1) else NetworkCallbackID(255)

    operator fun plus(b: Int): NetworkCallbackID = NetworkCallbackID((byteValue.toInt() + b).rem(256))

    operator fun minus(b: Int): NetworkCallbackID = NetworkCallbackID((byteValue.toInt() - b).rem(256))

}
