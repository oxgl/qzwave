package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.serialization.CommandDeserializer
import com.oxyggen.qzw.serialization.DeserializableCommandContext
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCExtended {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes(): Set<Byte> {
            val result = mutableSetOf<Byte>()
            (CommandClassID.EXTENDED_F1.byteValue..CommandClassID.EXTENDED_FF.byteValue).forEach {
                result.add(it.toByte())
            }
            return result
        }

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {
            val ccSecondByte = inputStream.getByte()

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }
}