package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.availableBytes
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getNBytes
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.transport.command.Command
import com.oxyggen.qzw.transport.factory.CommandFactory
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.*
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionApplicationCommandHandler {

    // ZW->HOST: REQ | 0x04 | rxStatus | sourceNode | cmdLength | pCmd[] | rxRSSIVal | securityKey

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.APPLICATION_COMMAND_HANDLER.byteValue)

        override suspend fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream, context)
                FrameType.RESPONSE -> Response()
            }
    }

    /************************************************************************************
     * ZW->HOST: REQ | 0x04 | rxStatus | sourceNode | cmdLength | pCmd[] |
     *                 rxRSSIVal | securityKey
     ************************************************************************************/
    class Request(
        val receiveStatus: ReceiveStatus,
        val sourceNode: Node,
        val command: Command,
        val receiveRSSIVal: Byte,
        val securityKey: SecurityKey
    ) : FunctionRequest(FunctionID.APPLICATION_COMMAND_HANDLER) {
        companion object {
            suspend fun deserialize(inputStream: InputStream, context: DeserializableFunctionContext): Request {
                val receiveStatus = ReceiveStatus.getByByteValue(inputStream.getByte())
                val sourceNodeID = NodeID.getByByteValue(inputStream.getByte())
                val sourceNode = context.network.getNode(sourceNodeID)
                val cmdLength = inputStream.getUByte().toInt()
                val cmdBytes = inputStream.getNBytes(cmdLength)
                val command = CommandFactory.deserializeCommand(cmdBytes.inputStream(), context, sourceNode)
                val receiveRSSIVal = if (inputStream.availableBytes() > 0) inputStream.getByte() else 0
                val securityKey =
                    if (inputStream.availableBytes() > 0) SecurityKey.getByByteValue(inputStream.getByte())
                        ?: SecurityKey.NONE else SecurityKey.NONE

                return Request(receiveStatus, sourceNode, command, receiveRSSIVal, securityKey)
            }
        }

        override fun getNode(network: Network): Node = sourceNode

        override fun toString(): String =
            buildParamList("source", sourceNode, "command", command, "status", receiveStatus)

    }

    class Response : FunctionResponse(FunctionID.APPLICATION_COMMAND_HANDLER)

}