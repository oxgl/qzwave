package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.getAllBytes
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putBytes
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.CommandDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableCommandContext
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.types.LibraryType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCTemplate {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.VERSION.byteValue)

        override suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.VERSION_GET -> Get.deserialize(inputStream, context)
                CommandID.VERSION_REPORT -> Report.deserialize(inputStream, context)
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

            suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Report =
                mapper.deserialize(
                    inputStream.getAllBytes(),
                    context.commandClassVersion
                )
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this, context.commandClassVersion))
        }

        override fun toString(): String =
            "CC $commandClassID - Command ${commandId}(" +
                    "out library type ${libraryType}, " +
                    "out prot vers ${protocolVersion}.${protocolSubVersion}, " +
                    "out appl vers ${applicationVersion}.${applicationSubVersion})"
    }
}