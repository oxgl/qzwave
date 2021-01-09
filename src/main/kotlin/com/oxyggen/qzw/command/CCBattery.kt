package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.from
import com.oxyggen.qzw.extensions.ge
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.types.BatteryChargingStatus
import com.oxyggen.qzw.types.BatteryReplaceStatus
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCBattery {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.BATTERY.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.BATTERY_GET -> Get.deserialize(commandData, context.version)
                CommandID.BATTERY_REPORT -> Report.deserialize(commandData, context.version)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.BATTERY, CommandID.BATTERY_GET) {
        companion object {
            fun deserialize(data: ByteArray, version: Int) = Get()
        }
    }

    class Report(
        val batteryLevel: Byte,
        val batteryChargingStatus: BatteryChargingStatus? = null,
        val rechargeable: Boolean? = null,
        val backupBattery: Boolean? = null,
        val overHeating: Boolean? = null,
        val lowFluid: Boolean? = null,
        val batteryReplaceStatus: BatteryReplaceStatus? = null,
        val disconnected: Boolean? = null,
        val lowTemperature: Boolean? = null,
    ) : Command(CommandClassID.BATTERY, CommandID.BATTERY_REPORT) {
        companion object {
            val mapper by lazy {
                mapper<Report> {
                    byte("batteryLevel")
                    bitMap(version = from(2)) {
                        bitRange("batteryChargingStatus", 6..7)
                        bit("rechargeable", 5)
                        bit("backupBattery", 4)
                        bit("overHeating", 3)
                        bit("lowFluid", 2)
                        bitRange("batteryReplaceStatus", 0..1)
                    }
                    bitMap(version = from(2)) {
                        bit("disconnected", 0)
                        bit("lowTemperature", 1, from(3))
                    }
                }
            }

            fun deserialize(data: ByteArray, version: Int) = mapper.deserialize<Report>(data, version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
        }
    }
}