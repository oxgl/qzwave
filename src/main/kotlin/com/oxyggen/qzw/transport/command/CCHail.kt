package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.transport.serialization.CommandDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableCommandContext
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCHail {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.HAIL.byteValue)

        override suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.HAIL -> Hail.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Hail : Command(CommandClassID.HAIL, CommandID.HAIL) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Hail()
        }
    }
}