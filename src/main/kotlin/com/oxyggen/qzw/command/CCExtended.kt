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
class CCExtended {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.EXTENDED.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val ccSecondByte = inputStream.getByte()
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }
}