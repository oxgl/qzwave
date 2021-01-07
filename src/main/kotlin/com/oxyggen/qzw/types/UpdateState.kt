@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")

package com.oxyggen.qzw.types

enum class UpdateState(val byteValue: Byte) {
    NODE_INFO_RECEIVED(0x84.toByte()),
    NODE_INFO_REQ_DONE(0x82.toByte()),
    NODE_INFO_REQ_FAILED(0x81.toByte()),
    ROUTING_PENDING(0x80.toByte()),
    NEW_ID_ASSIGNED(0x40),
    DELETE_DONE(0x20),
    SUC_ID(0x10);

    companion object : ByteToClass<UpdateState> {
        override fun getByByteValue(byteValue: Byte): UpdateState? =
            values().firstOrNull { it.byteValue == byteValue }
    }
}