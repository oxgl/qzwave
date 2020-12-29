package com.oxyggen.qzw.function

import com.oxyggen.qzw.cc.CommandClass
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryDeserializer
import com.oxyggen.qzw.serialization.BinaryDeserializerFunctionContext
import com.oxyggen.qzw.types.ReceiveStatus
import com.oxyggen.qzw.types.NodeID
import java.io.InputStream
import java.io.OutputStream

class FunctionApplicationCommandHandler(
    val receiveStatus: ReceiveStatus,
    val sourceNodeID: NodeID,
    val destinationNodeID: NodeID,
) : Function() {
    companion object : BinaryDeserializer<FunctionApplicationCommandHandler, BinaryDeserializerFunctionContext> {
        const val SIGNATURE = 0x04.toByte()

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryDeserializerFunctionContext
        ): FunctionApplicationCommandHandler {
            val receiveStatus = ReceiveStatus.getByByteValue(inputStream.getByte())
            val soureNodeId = inputStream.getUByte()
            val cmdLength = inputStream.getUByte().toInt()
            val cmd = inputStream.readNBytes(cmdLength)

            return FunctionApplicationCommandHandler(receiveStatus, soureNodeId, 0u)
        }
    }

}