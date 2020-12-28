package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinaryDeserializer
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.status.ReceiveStatus
import com.oxyggen.qzw.types.NodeID
import java.io.InputStream

class FunctionApplicationCommandHandler(
    val receiveStatus: ReceiveStatus,
    val sourceNodeID: NodeID,
    val destinationNodeID: NodeID
) : Function() {
    companion object : BinaryDeserializer<FunctionApplicationCommandHandler> {
        const val SIGNATURE = 0x04.toByte()

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionApplicationCommandHandler {
            val receiveStatus = ReceiveStatus(inputStream.getByte())
            val soureNodeId = inputStream.getUByte().toInt()
            val cmdLength = inputStream.getUByte().toInt()
            val cmd = inputStream.readNBytes(cmdLength)

            return FunctionApplicationCommandHandler(receiveStatus, soureNodeId, 0x00)
        }
    }
}            