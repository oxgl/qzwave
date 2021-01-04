package com.oxyggen.qzw.command

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.command.serial.serdef

class CommandNotification {


    class Report(
        val alarmType1: Byte,
        val alarmLevel1: Byte,
        val notificationStatus: Byte,
        val notificationType: Byte,
        val notificationEvent: Byte,


        ) : Command(CommandClassID.NOTIFICATION, CommandID.NOTIFICATION_REPORT) {

    }

    val x = serdef {
        byte("alarmType1")
        byte("alarmLevel2")
        byte("reserved")
        byte("notificationStatus")
        byte("notificationType")
        byte("notificationEvent")
        bitMap {
            number("sequenceEnabled", 7..7)
            number("parametersLength", 0..4)
        }
        byteCol("parameters", "@parametersLength")
        byte("sequenceNumber", "@sequenceEnabled")
    }

}