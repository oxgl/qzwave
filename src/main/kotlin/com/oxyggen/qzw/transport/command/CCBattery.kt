package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.from
import com.oxyggen.qzw.extensions.getAllBytes
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putBytes
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.CommandDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableCommandContext
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.BatteryChargingStatus
import com.oxyggen.qzw.types.BatteryReplaceStatus
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCBattery {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.BATTERY.byteValue)

        override suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

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

            suspend fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<Report>(
                    inputStream.getAllBytes(),
                    context.commandClassVersion
                )
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this, context.commandClassVersion))
        }

        override fun toString(): String = "CC $commandClassID - Command ${commandId}(level $batteryLevel%)"
    }
}