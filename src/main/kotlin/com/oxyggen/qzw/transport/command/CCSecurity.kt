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
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCSecurity {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.SECURITY.byteValue)

        override suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.SECURITY_NONCE_GET -> NonceGet.deserialize(inputStream, context)
                CommandID.SECURITY_NONCE_REPORT -> NonceReport.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class NonceGet : Command(CommandClassID.SECURITY, CommandID.SECURITY_NONCE_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = NonceGet()
        }
    }

    class NonceReport(
        val nonceBytes: ByteArray
    ) : Command(CommandClassID.SECURITY, CommandID.SECURITY_NONCE_REPORT) {
        companion object {
            private val mapper by lazy {
                mapper<NonceReport> {
                    byteCol("nonceBytes", length = "8")
                }
            }

            suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): NonceReport =
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
            "CC $commandClassID - Command ${commandId}(${nonceBytes.toList()})"
    }
}