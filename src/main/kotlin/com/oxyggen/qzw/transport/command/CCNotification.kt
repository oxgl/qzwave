package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.CommandDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableCommandContext
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCNotification {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.NOTIFICATION.byteValue)

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.NOTIFICATION_GET -> Get.deserialize(inputStream, context)
                CommandID.NOTIFICATION_SET -> Set.deserialize(inputStream, context)
                CommandID.NOTIFICATION_REPORT -> Report.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get(
        val v1alarmType: Byte,
        val notificationType: Byte,
        val notificationEvent: Byte
    ) : Command(CommandClassID.NOTIFICATION, CommandID.NOTIFICATION_GET) {
        companion object {
            val mapper by lazy {
                mapper<Report> {
                    byte("v1alarmType")
                    byte("notificationType")
                    byte("notificationEvent")
                }
            }

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Get =
                mapper.deserialize(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }

    }

    class Set(
        val notificationType: Byte,
        val notificationStatus: Byte
    ) : Command(CommandClassID.NOTIFICATION, CommandID.NOTIFICATION_SET) {
        companion object {
            val mapper by lazy {
                mapper<Report> {
                    byte("notificationType")
                    byte("notificationStatus")
                }
            }

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Set =
                mapper.deserialize(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }
    }

    class Report(
        val v1alarmType: Byte,
        val v1alarmLevel: Byte,
        val notificationStatus: Byte,
        val notificationType: Byte,
        val notificationEvent: Byte,
        val parameters: ByteArray,
        val sequenceNumber: Byte?,
    ) : Command(CommandClassID.NOTIFICATION, CommandID.NOTIFICATION_REPORT) {
        companion object {
            val mapper by lazy {
                mapper<Report> {
                    byte("v1alarmType")
                    byte("v1alarmLevel")
                    byte("#reserved")
                    byte("notificationStatus")
                    byte("notificationType")
                    byte("notificationEvent")
                    bitMap {
                        bit("#sequenceEnabled", 7)
                        bitRange("#parametersLength", 0..4)
                    }
                    byteCol("parameters", "@parametersLength")
                    byte("sequenceNumber", "@sequenceEnabled")
                }
            }

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Report =
                mapper.deserialize(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(Get.mapper.serialize(this, context.commandClassVersion))
        }

        override fun toString(): String = "CC ${commandClassID} - Command ${commandId}(" +
                "type $v1alarmType, " +
                "level $v1alarmLevel, " +
                "nStatus $notificationStatus, " +
                "nType $notificationType, " +
                "nEvent: $notificationEvent)"
    }
}