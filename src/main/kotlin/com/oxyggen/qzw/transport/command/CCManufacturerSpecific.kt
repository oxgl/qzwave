package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.getBitRange
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.CommandDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableCommandContext
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.DeviceIDType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
class CCManufacturerSpecific {

    companion object : CommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.MANUFACTURER_SPECIFIC.byteValue)

        override fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): Command {

            return when (val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())) {
                CommandID.MANUFACTURER_SPECIFIC_GET -> Get.deserialize(inputStream, context)
                CommandID.MANUFACTURER_SPECIFIC_REPORT -> Report.deserialize(inputStream, context)
                CommandID.DEVICE_SPECIFIC_GET -> DeviceSpecificGet.deserialize(inputStream, context)
                CommandID.DEVICE_SPECIFIC_REPORT -> DeviceSpecificReport.deserialize(inputStream, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.MANUFACTURER_SPECIFIC, CommandID.MANUFACTURER_SPECIFIC_GET) {
        companion object {
            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) = Get()
        }
    }

    class Report(
        val manufacturerID: Short,
        val productTypeID: Short,
        val productID: Short
    ) : Command(CommandClassID.MANUFACTURER_SPECIFIC, CommandID.MANUFACTURER_SPECIFIC_REPORT) {
        companion object {
            private val mapper by lazy {
                mapper<Report> {
                    int16("manufacturerID")
                    int16("productTypeID")
                    int16("productID")
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
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }
    }

    class DeviceSpecificGet(val deviceIDType: DeviceIDType) :
        Command(CommandClassID.MANUFACTURER_SPECIFIC, CommandID.DEVICE_SPECIFIC_GET) {
        companion object {
            private val mapper by lazy {
                mapper<Report> {
                    bitMap {
                        bitRange("deviceIDType", 0..2)
                    }
                }
            }

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext) =
                mapper.deserialize<DeviceSpecificGet>(
                    inputStream.readAllBytes(),
                    context.commandClassVersion
                )
        }

        override fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this, context.commandClassVersion))
        }
    }

    class DeviceSpecificReport(
        val deviceIDType: DeviceIDType,
        val deviceString: String?,
        val deviceBinary: ByteArray?
    ) :
        Command(CommandClassID.MANUFACTURER_SPECIFIC, CommandID.DEVICE_SPECIFIC_REPORT) {
        companion object {
            private const val DATA_FORMAT_UTF_8 = 0x00
            private const val DATA_FORMAT_BINARY = 0x01

            fun deserialize(inputStream: InputStream, context: DeserializableCommandContext): DeviceSpecificReport {
                val deviceIDByte = inputStream.getByte()
                val deviceIDType =
                    DeviceIDType.getByByteValue(deviceIDByte.getBitRange(0..2)) ?: DeviceIDType.FACTORY_DEFAULT
                val dataTypeByte = inputStream.getByte()
                val dataLength = dataTypeByte.getBitRange(0..4).toInt()
                val dataFormat = dataTypeByte.getBitRange(5..7).toInt()
                return if (dataFormat == DATA_FORMAT_UTF_8) {
                    DeviceSpecificReport(
                        deviceIDType,
                        inputStream.readNBytes(dataLength).toString(Charsets.UTF_8),
                        null
                    )
                } else {
                    return DeviceSpecificReport(deviceIDType, null, inputStream.readNBytes(dataLength))
                }
            }
        }
    }

}