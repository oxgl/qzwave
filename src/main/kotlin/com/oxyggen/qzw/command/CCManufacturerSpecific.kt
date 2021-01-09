package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.getBitRange
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryCommandDeserializer
import com.oxyggen.qzw.serialization.BinaryCommandDeserializerContext
import com.oxyggen.qzw.types.DeviceIDType
import com.oxyggen.qzw.types.LibraryType
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.print.attribute.standard.PrinterMoreInfoManufacturer

@OptIn(ExperimentalUnsignedTypes::class)
class CCManufacturerSpecific {

    companion object : BinaryCommandDeserializer {
        override fun getHandledSignatureBytes() = setOf(CommandClassID.MANUFACTURER_SPECIFIC.byteValue)

        override fun deserialize(inputStream: InputStream, context: BinaryCommandDeserializerContext): Command {
            val commandID = CommandID.getByByteValue(context.commandClassID, inputStream.getByte())
            val commandData = inputStream.readAllBytes()

            return when (commandID) {
                CommandID.MANUFACTURER_SPECIFIC_GET -> Get.deserialize(commandData, context)
                CommandID.MANUFACTURER_SPECIFIC_REPORT -> Report.deserialize(commandData, context)
                CommandID.DEVICE_SPECIFIC_REPORT -> DeviceSpecificReport.deserialize(commandData, context)
                else -> throw IOException("${context.commandClassID}: Not implemented command ${commandID}!")
            }
        }
    }


    class Get : Command(CommandClassID.MANUFACTURER_SPECIFIC, CommandID.MANUFACTURER_SPECIFIC_GET) {
        companion object {
            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext) = Get()
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

            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext): Report =
                mapper.deserialize(data, context.version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
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

            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext) =
                mapper.deserialize<DeviceSpecificGet>(data, context.version)
        }

        override fun serialize(outputStream: OutputStream, function: Function, version: Int) {
            super.serialize(outputStream, function, version)
            outputStream.write(mapper.serialize(this, version))
        }
    }

    class DeviceSpecificReport(
        val deviceIDType: DeviceIDType,
        val deviceString: String?,
        val deviceBinary: ByteArray?
    ) :
        Command(CommandClassID.MANUFACTURER_SPECIFIC, CommandID.DEVICE_SPECIFIC_REPORT) {
        companion object {
            fun deserialize(data: ByteArray, context: BinaryCommandDeserializerContext): DeviceSpecificReport {
                val deviceIDType =
                    DeviceIDType.getByByteValue(data[0].getBitRange(0..2)) ?: DeviceIDType.FACTORY_DEFAULT
                //val datalength = data[1].getBitRange(0..4).toInt()
                return if (data[1].getBitRange(5..7).toInt() == 0) {
                    DeviceSpecificReport(deviceIDType, data.drop(2).toByteArray().toString(Charsets.UTF_8), null)
                } else {
                    return DeviceSpecificReport(deviceIDType, null, data.drop(2).toByteArray())
                }
            }
        }
    }

}