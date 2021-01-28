package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.transport.command.Command
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.transport.factory.CommandFactory
import com.oxyggen.qzw.engine.network.NodeInfo
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.*
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionApplicationCommandHandler {

    // ZW->HOST: REQ | 0x04 | rxStatus | sourceNode | cmdLength | pCmd[] | rxRSSIVal | securityKey

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.APPLICATION_COMMAND_HANDLER.byteValue)

        override fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream, context)
                FrameType.RESPONSE -> Response()
            }
    }

    class Request(
        val receiveStatus: ReceiveStatus,
        val sourceNodeID: NodeID,
        val command: Command,
        val receiveRSSIVal: Byte,
        val securityKey: SecurityKey
    ) : FunctionRequest(FunctionID.APPLICATION_COMMAND_HANDLER) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableFunctionContext): Request {
                val receiveStatus = ReceiveStatus.getByByteValue(inputStream.getByte())
                val sourceNodeID = inputStream.getUByte()
                val currentNode = context.networkInfo.node[sourceNodeID] ?: NodeInfo.getInitial(sourceNodeID)
                val cmdLength = inputStream.getUByte().toInt()
                val cmdBytes = inputStream.readNBytes(cmdLength)
                val command = CommandFactory.deserializeCommand(cmdBytes.inputStream(), context, currentNode)
                val receiveRSSIVal = if (inputStream.available() > 0) inputStream.getByte() else 0
                val securityKey = if (inputStream.available() > 0) SecurityKey.getByByteValue(inputStream.getByte())
                    ?: SecurityKey.NONE else SecurityKey.NONE

                return Request(receiveStatus, sourceNodeID, command, receiveRSSIVal, securityKey)
            }
        }

        override fun toString(): String =
            "${functionID}(status $receiveStatus, source: $sourceNodeID, command: $command)"

    }

    class Response : FunctionResponse(FunctionID.APPLICATION_COMMAND_HANDLER)

}