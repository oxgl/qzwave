package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.types.LibraryType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCVersion {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.VERSION.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.VERSION_GET -> Get.deserialize(commandData, context)
                CommandID.VERSION_REPORT -> Report.deserialize(commandData, context)
                CommandID.VERSION_COMMAND_CLASS_GET -> CommandClassGet.deserialize(commandData, context)
                CommandID.VERSION_COMMAND_CLASS_REPORT -> CommandClassReport.deserialize(commandData, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.VERSION, CommandID.VERSION_GET) {
        companion object {
            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext) = Get()
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

    class CommandClassGet(val ccID: CommandClassID) :
        Command(CommandClassID.VERSION, CommandID.VERSION_COMMAND_CLASS_GET) {
        companion object {
            val mapper by lazy {
                mapper<CommandClassGet> {
                    byte("ccID")
                }
            }

            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext) =
                mapper.deserialize<CommandClassGet>(data, context.version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this))
        }
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

            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext) =
                mapper.deserialize<CommandClassReport>(data, context.version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this))
        }
    }
}