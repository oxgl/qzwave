package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.get
import com.oxyggen.qzw.extensions.getBitRange
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.factory.CommandFactory
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.types.EndpointID
import com.oxyggen.qzw.types.LibraryType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCMultiChannel {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.MULTI_CHANNEL.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.MULTI_CHANNEL_CMD_ENCAP -> CmdEncap.deserialize(commandData, context)
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
            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext): CmdEncap {
                val srcEndpointID = data[0].getBitRange(0..6)
                val dstEndpointID = data[1].getBitRange(0..6)
                val dstEndpointIsBitmask = data[1][7]

                val commandBytes = data.drop(2).toByteArray()
                val command = CommandFactory.deserializeCommand(commandBytes.inputStream(), context)
                return CmdEncap(srcEndpointID, dstEndpointID, dstEndpointIsBitmask, command)
            }
        }
    }

    class Report(
        val libraryType: LibraryType,
        val protocolVersion: Byte,
        val protocolSubVersion: Byte,
        val applicationVersion: Byte,
        val applicationSubVersion: Byte
    ) : Command(CommandClassID.VERSION, CommandID.VERSION_REPORT) {
        companion object {
            private val mapper by lazy {
                mapper<Report> {
                    byte("libraryType")
                    byte("protocolVersion")
                    byte("protocolSubVersion")
                    byte("applicationVersion")
                    byte("applicationSubVersion")
                }
            }

            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext): Report =
                mapper.deserialize(data, context.version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
        }

        override fun toString(): String =
            "CC ${commandClassID} - Command ${commandId}(" +
                    "out library type ${libraryType}, " +
                    "out prot vers ${protocolVersion}.${protocolSubVersion}, " +
                    "out appl vers ${applicationVersion}.${applicationSubVersion})"
    }
}