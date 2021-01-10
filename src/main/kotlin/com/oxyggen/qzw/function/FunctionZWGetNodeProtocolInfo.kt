package com.oxyggen.qzw.function

import com.oxyggen.qzw.device.DeviceBasicType
import com.oxyggen.qzw.device.DeviceGenericType
import com.oxyggen.qzw.device.DeviceSpecificType
import com.oxyggen.qzw.extensions.*
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionZWGetNodeProtocolInfo {

    // HOST->ZW: REQ | 0x41 | bNodeID
    // ZW->HOST: RES | 0x41 | nodeInfo (see figure above)

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_NODE_PROTOCOL_INFO.byteValue)

        override fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }


    class Request(val nodeID: NodeID) : FunctionRequest(FunctionID.ZW_GET_NODE_PROTOCOL_INFO) {
        companion object {
            private val mapper by lazy {
                mapper<Request> {
                    byte("nodeID")
                }
            }

            fun deserialize(inputStream: InputStream) = mapper.deserialize<Request>(inputStream.readAllBytes())
        }

        override fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this))
        }
    }


    class Response(
        val listening: Boolean,
        val optionalFunctionality: Boolean,
        val sensor1000ms: Boolean,
        val sensor250ms: Boolean,
        val deviceBasicType: DeviceBasicType,
        val deviceGenericType: DeviceGenericType,
        val deviceSpecificType: DeviceSpecificType
    ) : FunctionResponse(FunctionID.ZW_GET_NODE_PROTOCOL_INFO) {
        companion object {
            fun deserialize(inputStream: InputStream): Response {
                val capability = inputStream.getByte()
                val listening = capability[7]
                val security = inputStream.getByte()
                val optionalFunctionality = security[7]
                val sensor1000ms = security[6]
                val sensor250ms = security[5]
                inputStream.getByte()       // Reserved!
                val deviceBasicType = DeviceBasicType.getByByteValue(inputStream.getByte())
                    ?: throw IOException("Unknown basic device type!")
                val deviceGenericType = DeviceGenericType.getByByteValue(inputStream.getByte())
                    ?: throw IOException("Unknown generic device type!")
                val deviceSpecificType = DeviceSpecificType.getByByteValue(deviceGenericType, inputStream.getByte())
                    ?: DeviceSpecificType.NOT_USED
                return Response(
                    listening,
                    optionalFunctionality,
                    sensor1000ms,
                    sensor250ms,
                    deviceBasicType,
                    deviceGenericType,
                    deviceSpecificType
                )
            }
        }
    }

}