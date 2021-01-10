package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.*
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.serialization.CommandDeserializer
import com.oxyggen.qzw.serialization.DeserializableCommandContext
import com.oxyggen.qzw.serialization.SerializableCommandContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.pow
import kotlin.math.roundToInt

@OptIn(ExperimentalUnsignedTypes::class)
class CCSensorMultilevel {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.SENSOR_MULTILEVEL.byteValue)

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.SENSOR_MULTILEVEL_GET -> Get.deserialize(inputStream, context)
                CommandID.SENSOR_MULTILEVEL_REPORT -> Report.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.SENSOR_MULTILEVEL, CommandID.SENSOR_MULTILEVEL_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Get()
        }
    }

    class Report(
        val sensorType: Byte,
        val precision: Byte,
        val scale: Byte,
        val size: Byte,
        val sensorValues: List<Float>,
    ) : Command(CommandClassID.SENSOR_MULTILEVEL, CommandID.SENSOR_MULTILEVEL_REPORT) {
        companion object {

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Report {
                val sensorType = inputStream.getByte()
                val dataTypeInfo = inputStream.getByte()
                val precision = dataTypeInfo.getBitRange(5..7)
                val scale = dataTypeInfo.getBitRange(3..4)
                val size = dataTypeInfo.getBitRange(0..2)
                val sensorValues = mutableListOf<Float>()
                var remaining = size.toInt()
                var value = 0
                val divider = 10.toFloat().pow(precision.toInt())
                while (inputStream.available() > 0) {
                    val dataUByte = inputStream.getUByte()
                    value = value.shl(8).or(dataUByte.toInt())
                    remaining--
                    if (remaining == 0) {
                        sensorValues.add(value.toFloat() / divider)
                        remaining = size.toInt()
                        value = 0
                    }
                }
                return Report(sensorType, precision, scale, size, sensorValues)
            }
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.putByte(sensorType)
            outputStream.putByte(
                0x00.toByte().withBitRange(0..2, size).withBitRange(3..4, scale).withBitRange(5..7, precision)
            )
            val divider = 10.toFloat().pow(precision.toInt())
            for (value in sensorValues) {
                var rest = (value * divider).roundToInt()
                val byteArray = ByteArray(size.toInt())
                for (index in 0 until size) {
                    byteArray[index] = rest.and(0xff).toByte()
                    rest = rest.shr(8)
                }
                byteArray.reverse()
                outputStream.write(byteArray)
            }

        }

    }
}