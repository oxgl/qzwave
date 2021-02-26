@file:Suppress("RedundantSuspendModifier", "RedundantSuspendModifier", "RedundantSuspendModifier",
    "RedundantSuspendModifier", "RedundantSuspendModifier", "RedundantSuspendModifier", "RedundantSuspendModifier",
    "RedundantSuspendModifier", "RedundantSuspendModifier", "RedundantSuspendModifier", "RedundantSuspendModifier"
)

package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.NetworkCallbackKey
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.*
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionZWGetNodeProtocolInfo {

    // HOST->ZW: REQ | 0x41 | bNodeID
    // ZW->HOST: RES | 0x41 | nodeInfo (see figure INS13954-Instruction-Z-Wave-500-Series-Appl-Programmers-Guide-v6_8x_0x.pdf)
    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_NODE_PROTOCOL_INFO.byteValue)

        override suspend fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream, context)
                FrameType.RESPONSE -> Response.deserialize(inputStream, context)
            }
    }

    /************************************************************************************
     * HOST->ZW: REQ | 0x41 | bNodeID
     ************************************************************************************/
    class Request(val node: Node) : FunctionRequest(FunctionID.ZW_GET_NODE_PROTOCOL_INFO) {
        companion object {
            suspend fun deserialize(inputStream: InputStream, context: DeserializableFunctionContext): Request {
                val nodeID = NodeID.getByByteValue(inputStream.getByte())
                val node = context.network.getNode(nodeID)
                return Request(node)
            }
        }

        override fun getNode(network: Network): Node = node

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.put(node.nodeID)
        }

        override fun toString() = buildParamList("node", node)

    }

    /************************************************************************************
     * ZW->HOST: RES | 0x41 | nodeInfo (see figure
     * INS13954-Instruction-Z-Wave-500-Series-Appl-Programmers-Guide-v6_8x_0x.pdf)
     ************************************************************************************/
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
            suspend fun deserialize(inputStream: InputStream, context: DeserializableFunctionContext): Response {
                // We can't use mapper because DeviceSpecificType has 2 imports
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

        override fun toString(): String =
            buildParamList(
                "basic", deviceBasicType,
                "generic", deviceGenericType,
                "specific", deviceSpecificType,
                "listening", listening,
                "optionalFunctionality", optionalFunctionality,
                "sensor 1000ms", sensor1000ms,
                "sensor 250ms", sensor250ms
            )
    }
}