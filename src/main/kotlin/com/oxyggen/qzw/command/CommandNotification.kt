package com.oxyggen.qzw.command

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper

class CommandNotification {


    class Get(
        val v1alarmType: Byte,
        val notificationType: Byte,
        val notificationEvent: Byte
    ) : Command(CommandClassID.NOTIFICATION, CommandID.NOTIFICATION_GET) {
        companion object {
            private val mapper by lazy {
                mapper {
                    byte("v1alarmType")
                    byte("notificationType")
                    byte("notificationEvent")
                }
            }
        }
    }

    class Set(
        val notificationType: Byte,
        val notificationStatus: Byte
    ) : Command(CommandClassID.NOTIFICATION, CommandID.NOTIFICATION_SET) {
        companion object {
            private val mapper by lazy {
                mapper {
                    byte("notificationType")
                    byte("notificationStatus")
                }
            }
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
                mapper {
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
        }
    }
}