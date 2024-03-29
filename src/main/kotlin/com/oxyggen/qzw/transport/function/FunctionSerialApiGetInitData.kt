package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.*
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.Capabilities
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import com.oxyggen.qzw.utils.BitmaskUtils
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionSerialApiGetInitData {
    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.SERIAL_API_GET_INIT_DATA.byteValue)

        override suspend fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    /************************************************************************************
     * HOST->ZW: REQ | 0x02
     ************************************************************************************/
    class Request : FunctionRequest(FunctionID.SERIAL_API_GET_INIT_DATA) {
        companion object {
            fun deserialize(inputStream: InputStream) = Request()
        }

        override fun getNode(network: Network): Node = Node.SERIAL_API
    }


    /************************************************************************************
     * Controller:
     * ZW->HOST: RES | 0x02 | ver | capabilities | 29 | nodes[29] | chip_type | chip_version
     * Slave (not used):
     * ZW->HOST: RES | 0x02 | ver | capabilities | 0 | chip_type | chip_version
     ************************************************************************************/
    class Response(
        val serialApiVersion: Byte,
        val capabilities: Capabilities,
        val nodeIDs: Set<NodeID>,
        val chipType: Byte,
        val chipVersion: Byte
    ) : FunctionResponse(FunctionID.SERIAL_API_GET_INIT_DATA) {

        companion object {
            suspend fun deserialize(inputStream: InputStream): Response {
                val serialApiVersion = inputStream.getByte()
                val capabilities = Capabilities.getByByteValue(inputStream.getByte())
                val nodeCount = inputStream.getUByte().toInt()
                val nodeBitmap = if (nodeCount > 0) inputStream.getNBytes(nodeCount) else ByteArray(0)
                val nodes = BitmaskUtils.decompressBitmaskToObjectSet(nodeBitmap, parser = NodeID::getByByteValue)
                val chipType = inputStream.getByte()
                val chipVersion = inputStream.getByte()
                return Response(
                    serialApiVersion,
                    capabilities,
                    nodes,
                    chipType,
                    chipVersion
                )
            }
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putByte(serialApiVersion)
            outputStream.putByte(capabilities.byteValue)
            outputStream.putByte(if (!nodeIDs.isNullOrEmpty()) 29 else 0)
            if (!nodeIDs.isNullOrEmpty()) {
                val nodeBitmask = BitmaskUtils.compressObjectSetToBitmask(nodeIDs, resultBytes = 29)
                outputStream.putBytes(nodeBitmask)
            }
            outputStream.putByte(chipType)
            outputStream.putByte(chipVersion)
        }

        override fun getNode(network: Network): Node = Node.SERIAL_API

        override fun toString() = buildParamList(
            "version", serialApiVersion,
            "capabilities", "[$capabilities]",
            "chip type/version", "$chipType/$chipVersion",
            "nodes", "$nodeIDs"
        )
    }
}