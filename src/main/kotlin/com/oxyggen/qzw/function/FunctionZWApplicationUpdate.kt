package com.oxyggen.qzw.function

import com.oxyggen.qzw.cc.CommandClass
import com.oxyggen.qzw.device.DeviceBasicType
import com.oxyggen.qzw.device.DeviceGenericType
import com.oxyggen.qzw.device.DeviceSpecificType
import com.oxyggen.qzw.driver.BinaryDeserializer
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.status.UpdateState
import com.oxyggen.qzw.types.NodeID
import org.apache.logging.log4j.kotlin.Logging
import org.apache.logging.log4j.kotlin.logger
import java.io.IOException
import java.io.InputStream

class FunctionZWApplicationUpdate(
    val updateState: UpdateState,
    val nodeID: NodeID,
    val deviceBasicType: DeviceBasicType,
    val deviceGenericType: DeviceGenericType,
    val deviceSpecificType: DeviceSpecificType,
    val commandClassList: List<CommandClass>
) : Function() {
    companion object : BinaryDeserializer<FunctionZWApplicationUpdate>, Logging {
        const val SIGNATURE = 0x49.toByte()

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(signatureByte: Byte, inputStream: InputStream): FunctionZWApplicationUpdate {
            val updateState =
                UpdateState.getByByteValue(inputStream.getByte()) ?: throw IOException("Invalid update state!")
            val nodeId = inputStream.getUByte().toInt()
            val cmdLength = inputStream.getUByte().toInt()
            val deviceBasicType =
                DeviceBasicType.getByByteValue(inputStream.getByte()) ?: throw IOException("Invalid basic device type!")
            val deviceGenericType = DeviceGenericType.getByByteValue(inputStream.getByte())
                ?: throw IOException("Invalid generic device type!")
            val deviceSpecificType = DeviceSpecificType.getByByteValue(deviceGenericType, inputStream.getByte())
                ?: DeviceSpecificType.NOT_USED

            val ccBytes = inputStream.readNBytes(cmdLength - 3)

            val commandClassList = mutableListOf<CommandClass>()

            ccBytes.forEach {
                val cc = CommandClass.getByByteValue(it)
                if (cc!=null) commandClassList.add(cc)
            }

            return FunctionZWApplicationUpdate(
                updateState,
                nodeId,
                deviceBasicType,
                deviceGenericType,
                deviceSpecificType,
                commandClassList
            )
        }
    }

    override fun toString(): String {
        var ccListDescr = ""
        commandClassList.forEach {
            if (!ccListDescr.isBlank()) ccListDescr += ", "
            ccListDescr += it.toString()
        }

        return "ZW_APPLICATION_UPDATE(state: ${updateState.toString()}, " +
                "source: ${nodeID}, " +
                "device: ${deviceBasicType.toString()} / ${deviceGenericType.toString()} / ${deviceSpecificType.toString()}, " +
                "CC: [$ccListDescr]"

    }
}            