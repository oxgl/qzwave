package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.CommandDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableCommandContext
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.LibraryType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCVersion {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.VERSION.byteValue)

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.VERSION_GET -> Get.deserialize(inputStream, context)
                CommandID.VERSION_REPORT -> Report.deserialize(inputStream, context)
                CommandID.VERSION_COMMAND_CLASS_GET -> CommandClassGet.deserialize(inputStream, context)
                CommandID.VERSION_COMMAND_CLASS_REPORT -> CommandClassReport.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.VERSION, CommandID.VERSION_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Get()
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

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Report =
                mapper.deserialize(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }

        override fun toString(): String =
            "CC ${commandClassID} - Command ${commandId}(" +
                    "library type ${libraryType}, " +
                    "prot vers ${protocolVersion}.${protocolSubVersion}, " +
                    "appl vers ${applicationVersion}.${applicationSubVersion})"
    }

    class CommandClassGet(val ccID: CommandClassID) :
        Command(CommandClassID.VERSION, CommandID.VERSION_COMMAND_CLASS_GET) {
        companion object {
            val mapper by lazy {
                mapper<CommandClassGet> {
                    byte("ccID")
                }
            }

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<CommandClassGet>(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }

        override fun toString() = buildParamList("ccID", ccID)
    }

    class CommandClassReport(val ccID: CommandClassID, val ccVersion: Byte) :
        Command(CommandClassID.VERSION, CommandID.VERSION_COMMAND_CLASS_REPORT) {
        companion object {
            val mapper by lazy {
                mapper<CommandClassGet> {
                    byte("ccID")
                    byte("ccVersion")
                }
            }

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<CommandClassReport>(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }

        override fun toString(): String = buildParamList("ccID", ccID, "ccVersion", ccVersion)
    }
}