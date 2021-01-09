package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.getBitRange
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.extensions.withBitRange
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.types.LibraryType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import kotlin.math.pow
import kotlin.math.roundToInt

@OptIn(ExperimentalUnsignedTypes::class)
class CCSensorMultilevel {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.SENSOR_MULTILEVEL.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.SENSOR_MULTILEVEL_GET -> Get.deserialize(commandData, context)
                CommandID.SENSOR_MULTILEVEL_REPORT -> Report.deserialize(commandData, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.SENSOR_MULTILEVEL, CommandID.SENSOR_MULTILEVEL_GET) {
        companion object {
            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext) = Get()
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

            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext): Report {
                val sensorType = data[0]
                val precision = data[1].getBitRange(5..7)
                val scale = data[1].getBitRange(3..4)
                val size = data[1].getBitRange(0..2)
                val sensorValues = mutableListOf<Float>()
                var remaining = size.toInt()
                var value = 0
                val divider = 10.toFloat().pow(precision.toInt())
                for (index in 2 until data.size) {
                    value = value.shl(8).or(data[index].toUByte().toInt())
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

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
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