package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.get
import com.oxyggen.qzw.extensions.getBitRange
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.factory.CommandFactory
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.serialization.CommandDeserializer
import com.oxyggen.qzw.serialization.DeserializableCommandContext
import com.oxyggen.qzw.types.EndpointID
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCMultiChannel {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.MULTI_CHANNEL.byteValue)

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.MULTI_CHANNEL_CMD_ENCAP -> CmdEncap.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class CmdEncap(
        val srcEndpointID: EndpointID,
        val dstEndpointID: EndpointID,
        val dstEndpointIsBitmask: Boolean,
        val command: Command
    ) :
        Command(CommandClassID.MULTI_CHANNEL, CommandID.MULTI_CHANNEL_CMD_ENCAP) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): CmdEncap {
                val srcEndpointByte = inputStream.getByte()
                val srcEndpointID = srcEndpointByte.getBitRange(0..6)
                val dstEndpointByte = inputStream.getByte()
                val dstEndpointID = dstEndpointByte.getBitRange(0..6)
                val dstEndpointIsBitmask = dstEndpointByte[7]

                val command = CommandFactory.deserializeCommand(inputStream, context, context.currentNode)
                return CmdEncap(srcEndpointID, dstEndpointID, dstEndpointIsBitmask, command)
            }
        }
    }
}