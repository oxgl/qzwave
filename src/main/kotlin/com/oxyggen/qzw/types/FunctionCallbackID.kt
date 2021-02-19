package com.oxyggen.qzw.types

@OptIn(ExperimentalUnsignedTypes::class)
data class FunctionCallbackID(override val byteValue: Byte) : TypeToByte, Comparable<FunctionCallbackID> {

    constructor(i: Int) : this(i.toByte())

    companion object : ByteToType<FunctionCallbackID> {
        val MAX_VALUE = FunctionCallbackID(255)
        val MIN_VALUE = FunctionCallbackID(1)
        val ALL_VALID = FunctionCallbackIDIterator(MIN_VALUE, MAX_VALUE)
        override fun getByByteValue(byteValue: Byte): FunctionCallbackID = FunctionCallbackID(byteValue)
    }

    override fun compareTo(other: FunctionCallbackID): Int =
        this.byteValue.toUByte().compareTo(other.byteValue.toUByte())

    operator fun inc(): FunctionCallbackID =
        if (this < MAX_VALUE) FunctionCallbackID(byteValue.toUByte().toInt() + 1) else FunctionCallbackID(0)

    operator fun dec(): FunctionCallbackID =
        if (this > MIN_VALUE) FunctionCallbackID(byteValue.toUByte().toInt() - 1) else FunctionCallbackID(255)

    operator fun plus(b: Int): FunctionCallbackID = FunctionCallbackID((byteValue.toInt() + b).rem(256))

    operator fun minus(b: Int): FunctionCallbackID = FunctionCallbackID((byteValue.toInt() - b).rem(256))

}
