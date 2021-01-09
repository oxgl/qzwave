package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.types.NodeID
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCWakeUp {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.WAKE_UP.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.WAKE_UP_INTERVAL_GET -> IntervalGet.deserialize(commandData, context.version)
                CommandID.WAKE_UP_INTERVAL_SET -> IntervalSet.deserialize(commandData, context.version)
                CommandID.WAKE_UP_INTERVAL_REPORT -> IntervalReport.deserialize(commandData, context.version)
                CommandID.WAKE_UP_NOTIFICATION -> Notification.deserialize(commandData, context.version)
                CommandID.WAKE_UP_NO_MORE_INFORMATION -> NoMoreInformation.deserialize(commandData, context.version)
                CommandID.WAKE_UP_INTERVAL_CAPABILITIES_GET -> IntervalCapabilitiesGet.deserialize(
                    commandData,
                    context.version
                )
                CommandID.WAKE_UP_INTERVAL_CAPABILITIES_REPORT -> IntervalCapabilitiesReport.deserialize(
                    commandData,
                    context.version
                )
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }

    class IntervalGet : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_GET) {
        companion object {
            fun deserialize(data: ByteArray, version: Int) = Notification()
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

            fun deserialize(data: ByteArray, version: Int) = mapper.deserialize<IntervalSet>(data, version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
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

            fun deserialize(data: ByteArray, version: Int) = mapper.deserialize<IntervalReport>(data, version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
        }
    }

    class Notification : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_NOTIFICATION) {
        companion object {
            fun deserialize(data: ByteArray, version: Int) = Notification()
        }
    }

    class NoMoreInformation : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_NO_MORE_INFORMATION) {
        companion object {
            fun deserialize(data: ByteArray, version: Int) = NoMoreInformation()
        }
    }

    class IntervalCapabilitiesGet : Command(CommandClassID.WAKE_UP, CommandID.WAKE_UP_INTERVAL_CAPABILITIES_GET) {
        companion object {
            fun deserialize(data: ByteArray, version: Int) = IntervalCapabilitiesGet()
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

            fun deserialize(data: ByteArray, version: Int) =
                mapper.deserialize<IntervalCapabilitiesReport>(data, version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
        }
    }

}