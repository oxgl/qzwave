package com.oxyggen.qzw.factory

import com.oxyggen.qzw.command.*
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.node.NodeInfo
import com.oxyggen.qzw.serialization.DeserializableCommandContext
import com.oxyggen.qzw.serialization.DeserializableHandler
import com.oxyggen.qzw.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.NodeID
import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CommandFactory {
    companion object : Logging {
        private val bdh by lazy {
            DeserializableHandler<Command, DeserializableCommandContext>(
                objectDescription = "command class",
                CCBattery::class,
                CCExtended::class,
                CCHail::class,
                CCManufacturerSpecific::class,
                CCMultiChannel::class,
                CCNotification::class,
                CCSensorMultilevel::class,
                CCVersion::class,
                CCWakeUp::class,
            )
        }

        fun deserializeCommand(
            inputStream: InputStream,
            functionContext: DeserializableFunctionContext,
            currentNode: NodeInfo
        ): Command {
            val ccByte = inputStream.getByte()
            val commandClassID = CommandClassID.getByByteValueVer(ccByte)
                ?: throw IOException("Unknown command class signature byte 0x%02x!".format(ccByte))
            val context = DeserializableCommandContext(
                functionContext = functionContext,
                commandClassID = commandClassID,
                currentNode = currentNode
            )

            return bdh.deserialize(inputStream, context)
        }

    }
}