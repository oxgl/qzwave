package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.from
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.serialization.CommandDeserializer
import com.oxyggen.qzw.serialization.DeserializableCommandContext
import com.oxyggen.qzw.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.BatteryChargingStatus
import com.oxyggen.qzw.types.BatteryReplaceStatus
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCBattery {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.BATTERY.byteValue)

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.BATTERY_GET -> Get.deserialize(inputStream, context)
                CommandID.BATTERY_REPORT -> Report.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.BATTERY, CommandID.BATTERY_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Get()
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

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<Report>(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }
    }
}