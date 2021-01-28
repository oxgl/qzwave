package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.DeviceBasicType
import com.oxyggen.qzw.types.DeviceGenericType
import com.oxyggen.qzw.types.DeviceSpecificType
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import com.oxyggen.qzw.types.UpdateState
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionZWApplicationUpdate {
    companion object : BinaryFunctionDeserializer {
        private val SIGNATURE = FunctionID.ZW_APPLICATION_UPDATE.byteValue

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        override fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> throw IOException("Invalid frame ${context.frameType} / ${context.functionID}")
            }
    }

    class Request(
        val updateState: UpdateState,
        val nodeID: NodeID,
        val deviceBasicType: DeviceBasicType,
        val deviceGenericType: DeviceGenericType,
        val deviceSpecificType: DeviceSpecificType,
        val commandClassList: List<CommandClassID>
    ) : FunctionRequest(FunctionID.ZW_APPLICATION_UPDATE) {

        companion object {
            fun deserialize(inputStream: InputStream): Request {
                val updateState =
                    UpdateState.getByByteValue(inputStream.getByte()) ?: throw IOException("Invalid update state!")
                val nodeID = inputStream.getUByte()
                val cmdLength = inputStream.getUByte().toInt()
                val deviceBasicType =
                    DeviceBasicType.getByByteValue(inputStream.getByte())
                        ?: throw IOException("Invalid basic device type!")
                val deviceGenericType = DeviceGenericType.getByByteValue(inputStream.getByte())
                    ?: throw IOException("Invalid generic device type!")
                val deviceSpecificType = DeviceSpecificType.getByByteValue(deviceGenericType, inputStream.getByte())
                    ?: DeviceSpecificType.NOT_USED

                val ccBytes = inputStream.readNBytes(cmdLength - 3)

                val commandClassList = mutableListOf<CommandClassID>()

                ccBytes.forEach {
                    val cc = CommandClassID.getByByteValue(it)
                    if (cc != null) commandClassList.add(cc)
                }

                return Request(
                    updateState,
                    nodeID,
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
                if (ccListDescr.isNotBlank()) ccListDescr += ", "
                ccListDescr += it.toString()
            }

            return "$functionID(state: $updateState, " +
                    "source: ${nodeID}, " +
                    "device: $deviceBasicType / $deviceGenericType / $deviceSpecificType, " +
                    "CC: [$ccListDescr]"

        }
    }

}