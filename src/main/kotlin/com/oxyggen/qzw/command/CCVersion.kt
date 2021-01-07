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

class CCVersion {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.VERSION.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.VERSION_GET -> Get.deserialize(commandData)
                CommandID.VERSION_REPORT -> Report.deserialize(commandData)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.VERSION, CommandID.VERSION_GET) {
        companion object {
            fun deserialize(data: ByteArray) = Get()
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
            fun deserialize(data: ByteArray): Report {
                val libraryType =
                    LibraryType.getByByteValue(data[0]) ?: throw IOException("CCVersion: Invalid library type!")
                return Report(libraryType, data[1], data[2], data[3], data[4])
            }
        }

        override fun serialize(outputStream: OutputStream, function: Function) {
            super.serialize(outputStream, function)
            outputStream.putByte(libraryType.byteValue)
            outputStream.putByte(protocolVersion)
            outputStream.putByte(protocolSubVersion)
            outputStream.putByte(applicationVersion)
            outputStream.putByte(applicationSubVersion)
        }

        override fun toString(): String =
            "CC ${commandClassId} - Command ${commandId}(" +
                    "out library type ${libraryType}, " +
                    "out prot vers ${protocolVersion}.${protocolSubVersion}, " +
                    "out appl vers ${applicationVersion}.${applicationSubVersion})"
    }
}