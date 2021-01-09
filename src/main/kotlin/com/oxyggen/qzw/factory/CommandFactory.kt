package com.oxyggen.qzw.factory

import com.oxyggen.qzw.command.*
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.serialization.BinaryDeserializerHandler
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.CommandClassID
import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CommandFactory {
    companion object : Logging {
        private val bdh by lazy {
            BinaryDeserializerHandler<Command, BinaryCommandDeserializerContext>(
                objectDescription = "command class",
                CCHail::class,
                CCBattery::class,
                CCNotification::class,
                CCVersion::class,
                CCWakeUp::class,
                CCSensorMultilevel::class,
                CCExtended::class
            )
        }

        fun deserializeCommand(
            inputStream: InputStream,
            functionContext: BinaryFunctionDeserializerContext,
        ): Command {
            val version = 1
            val ccByte = inputStream.getByte()
            val commandClassID = CommandClassID.getByByteValueVer(ccByte)
                ?: throw IOException("Unknown command class signature byte 0x%02x!".format(ccByte))
            val context = BinaryCommandDeserializerContext(
                frameID = functionContext.frameID,
                frameType = functionContext.frameType,
                functionID = functionContext.functionID,
                commandClassID = commandClassID,
                version = version
            )

            return bdh.deserialize(inputStream, context)
        }

    }
}