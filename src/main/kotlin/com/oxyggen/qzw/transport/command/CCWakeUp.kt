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
import com.oxyggen.qzw.types.NodeID
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCWakeUp {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.WAKE_UP.byteValue)

        override suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.WAKE_UP_INTERVAL_GET -> IntervalGet.deserialize(inputStream, context)
                CommandID.WAKE_UP_INTERVAL_SET -> IntervalSet.deserialize(inputStream, context)
                CommandID.WAKE_UP_INTERVAL_REPORT -> IntervalReport.deserialize(inputStream, context)
                CommandID.WAKE_UP_NOTIFICATION -> Notification.deserialize(inputStream, context)
                CommandID.WAKE_UP_NO_MORE_INFORMATION -> NoMoreInformation.deserialize(inputStream, context)
                CommandID.WAKE_UP_INTERVAL_CAPABILITIES_GET -> IntervalCapabilitiesGet.deserialize(inputStream, context)
                CommandID.WAKE_UP_INTERVAL_CAPABILITIES_REPORT -> IntervalCapabilitiesReport.deserialize(
                    inputStream,
                    context
                )
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }

    class IntervalGet : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Notification()
        }
    }

    class IntervalSet(val seconds: Int, nodeID: NodeID) :
        Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_SET) {
        companion object {
            val mapper by lazy {
                mapper<IntervalSet> {
                    int24("seconds")
                    byte("nodeID")
                }
            }

            suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<IntervalSet>(
                    inputStream.getAllBytes(),
                    context.commandClassVersion
                )
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this, context.commandClassVersion))
        }
    }


    class IntervalReport(val seconds: Int, nodeID: NodeID) :
        Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_REPORT) {
        companion object {
            val mapper by lazy {
                mapper<IntervalReport> {
                    int24("seconds")
                    byte("nodeID")
                }
            }

            suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<IntervalReport>(
                    inputStream.getAllBytes(),
                    context.commandClassVersion
                )
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this, context.commandClassVersion))
        }
    }

    class Notification : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_NOTIFICATION) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Notification()
        }
    }

    class NoMoreInformation : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_NO_MORE_INFORMATION) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                NoMoreInformation()
        }
    }

    class IntervalCapabilitiesGet : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_CAPABILITIES_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                IntervalCapabilitiesGet()
        }
    }

    class IntervalCapabilitiesReport(
        val minimumWakeUpInterval: Int,
        val maximumWakeUpInterval: Int,
        val defaultWakeUpInterval: Int,
        val wakeUpIntervalSteps: Int
    ) : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_CAPABILITIES_REPORT) {
        companion object {
            val mapper by lazy {
                mapper<IntervalCapabilitiesReport> {
                    int24("minimumWakeUpInterval")
                    int24("maximumWakeUpInterval")
                    int24("defaultWakeUpInterval")
                    int24("wakeUpIntervalSteps")
                }
            }

            suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<IntervalCapabilitiesReport>(
                    inputStream.getAllBytes(),
                    context.commandClassVersion
                )
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this, context.commandClassVersion))
        }
    }

}