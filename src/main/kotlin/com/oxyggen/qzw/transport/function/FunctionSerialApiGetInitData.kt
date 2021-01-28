package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putByte
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

        override fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    class Request : FunctionRequest(FunctionID.SERIAL_API_GET_INIT_DATA) {
        companion object {
            fun deserialize(inputStream: InputStream) = Request()
        }
    }

    class Response(
        val serialApiVersion: Byte,
        val capabilities: Capabilities,
        val nodes: Set<NodeID>,
        val chipType: Byte,
        val chipVersion: Byte
    ) : FunctionResponse(FunctionID.SERIAL_API_GET_INIT_DATA) {

        companion object {
            fun deserialize(inputStream: InputStream): Response {
                val serialApiVersion = inputStream.getByte()
                val capabilities = Capabilities.getByByteValue(inputStream.getByte())
                val nodeCount = inputStream.getUByte().toInt()
                val nodeBitmap = if (nodeCount > 0) inputStream.readNBytes(nodeCount) else ByteArray(0)
                val nodes = BitmaskUtils.decompressBitmaskToUByteSet(nodeBitmap)
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


        override fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putByte(serialApiVersion)
            outputStream.putByte(capabilities.byteValue)
            outputStream.putByte(if (!nodes.isNullOrEmpty()) 29 else 0)
            if (!nodes.isNullOrEmpty()) {
                val nodeBitmask = BitmaskUtils.compressUByteSetToBitmask(nodes, resultBytes = 29)
                outputStream.write(nodeBitmask)
            }
            outputStream.putByte(chipType)
            outputStream.putByte(chipVersion)
        }

        override fun toString() = buildParamList(
            "version", serialApiVersion,
            "capabilities", "[$capabilities]",
            "chip type/version", "$chipType/$chipVersion",
            "nodes", "$nodes"
        )
    }
}