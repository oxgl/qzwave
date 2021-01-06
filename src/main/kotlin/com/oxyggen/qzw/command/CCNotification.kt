package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import java.io.InputStream

class CCNotification {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.NOTIFICATION.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.NOTIFICATION_GET -> Get.deserialize(commandData)
                CommandID.NOTIFICATION_SET -> Set.deserialize(commandData)
                CommandID.NOTIFICATION_REPORT -> Report.deserialize(commandData)
                else -> throw TODO("Not implemented command!")
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

            fun deserialize(data: ByteArray): Get = mapper.deserialize(data)
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

            fun deserialize(data: ByteArray): Set = mapper.deserialize(data)
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

            fun deserialize(data: ByteArray): Report = mapper.deserialize(data)
        }
    }
}