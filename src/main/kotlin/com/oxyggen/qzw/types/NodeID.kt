package com.oxyggen.qzw.types

@OptIn(ExperimentalUnsignedTypes::class)
data class NodeID(override val byteValue: Byte) : TypeToByte {

    constructor(i: Int) : this(i.toByte())

    companion object : ByteToType<NodeID> {
        val SERIAL_API = NodeID(0x00)
        override fun getByByteValue(byteValue: Byte): NodeID = NodeID(byteValue)
    }

    override fun toString(): String = byteValue.toString()
}

