package com.oxyggen.qzw.function

import com.oxyggen.qzw.cc.CommandClass
import com.oxyggen.qzw.device.DeviceBasicType
import com.oxyggen.qzw.device.DeviceGenericType
import com.oxyggen.qzw.device.DeviceSpecificType
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionId
import com.oxyggen.qzw.types.NodeID
import com.oxyggen.qzw.types.UpdateState
import java.io.IOException
import java.io.InputStream

abstract class FunctionZWApplicationUpdate : Function() {
    companion object : BinaryFunctionDeserializer {
        private val SIGNATURE = FunctionId.ZW_APPLICATION_UPDATE.byteValue

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(SIGNATURE)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> {
                    val updateState =
                        UpdateState.getByByteValue(inputStream.getByte()) ?: throw IOException("Invalid update state!")
                    val nodeId = inputStream.getUByte()
                    val cmdLength = inputStream.getUByte().toInt()
                    val deviceBasicType =
                        DeviceBasicType.getByByteValue(inputStream.getByte())
                            ?: throw IOException("Invalid basic device type!")
                    val deviceGenericType = DeviceGenericType.getByByteValue(inputStream.getByte())
                        ?: throw IOException("Invalid generic device type!")
                    val deviceSpecificType = DeviceSpecificType.getByByteValue(deviceGenericType, inputStream.getByte())
                        ?: DeviceSpecificType.NOT_USED

                    val ccBytes = inputStream.readNBytes(cmdLength - 3)

                    val commandClassList = mutableListOf<CommandClass>()

                    ccBytes.forEach {
                        val cc = CommandClass.getByByteValue(it)
                        if (cc != null) commandClassList.add(cc)
                    }

                    Request(
                        updateState,
                        nodeId,
                        deviceBasicType,
                        deviceGenericType,
                        deviceSpecificType,
                        commandClassList
                    )
                }
                FrameType.RESPONSE -> throw IOException("Invalid frame RES / ZW_APPLICATION_UPDATE")
            }
    }

    class Request(
        val updateState: UpdateState,
        val nodeID: NodeID,
        val deviceBasicType: DeviceBasicType,
        val deviceGenericType: DeviceGenericType,
        val deviceSpecificType: DeviceSpecificType,
        val commandClassList: List<CommandClass>
    ) : FunctionRequest() {


        override fun toString(): String {
            var ccListDescr = ""
            commandClassList.forEach {
                if (ccListDescr.isNotBlank()) ccListDescr += ", "
                ccListDescr += it.toString()
            }

            return "ZW_APPLICATION_UPDATE(state: $updateState, " +
                    "source: ${nodeID}, " +
                    "device: $deviceBasicType / $deviceGenericType / $deviceSpecificType, " +
                    "CC: [$ccListDescr]"

        }
    }

}