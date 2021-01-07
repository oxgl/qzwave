package com.oxyggen.qzw.function

import com.oxyggen.qzw.command.Command
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.factory.CommandFactory
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.*
import java.io.IOException
import java.io.InputStream

@ExperimentalUnsignedTypes
abstract class FunctionApplicationCommandHandler {

    // ZW->HOST: REQ | 0x04 | rxStatus | sourceNode | cmdLength | pCmd[] | rxRSSIVal | securityKey

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.APPLICATION_COMMAND_HANDLER.byteValue)

        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
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
            fun deserialize(inputStream: InputStream, context: BinaryFunctionDeserializerContext): Request {
                val receiveStatus = ReceiveStatus.getByByteValue(inputStream.getByte())
                val sourceNodeID = inputStream.getUByte()
                val cmdLength = inputStream.getUByte().toInt()
                val cmdBytes = inputStream.readNBytes(cmdLength)
                val command = CommandFactory.deserializeCommand(cmdBytes.inputStream(), context)
                val receiveRSSIVal = if (inputStream.available() > 0) inputStream.getByte() else 0
                val securityKey = if (inputStream.available() > 0) SecurityKey.getByByteValue(inputStream.getByte())
                    ?: SecurityKey.NONE else SecurityKey.NONE

                return Request(receiveStatus, sourceNodeID, command, receiveRSSIVal, securityKey)
            }
        }

        override fun toString(): String =
            "${functionId}(status $receiveStatus, source: $sourceNodeID, command: $command)"

    }

    class Response : FunctionResponse(FunctionID.APPLICATION_COMMAND_HANDLER)

}