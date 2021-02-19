package com.oxyggen.qzw.types

@OptIn(ExperimentalUnsignedTypes::class)
data class EndpointID(override val byteValue: Byte) : TypeToByte {

    constructor(i: Int) : this(i.toByte())

    companion object : ByteToType<EndpointID> {
        override fun getByByteValue(byteValue: Byte): EndpointID = EndpointID(byteValue)
    }
}