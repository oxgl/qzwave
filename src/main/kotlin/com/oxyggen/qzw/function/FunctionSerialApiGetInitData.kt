package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.Capabilities
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import com.oxyggen.qzw.utils.BitmaskUtils
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionSerialApiGetInitData {
    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.SERIAL_API_GET_INIT_DATA.byteValue)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request()
                FrameType.RESPONSE -> {
                    val serialApiVersion = inputStream.getByte()
                    val capabilities = Capabilities.getByByteValue(inputStream.getByte())
                    val nodeCount = inputStream.getUByte().toInt()
                    val nodeBitmap = if (nodeCount > 0) inputStream.readNBytes(nodeCount) else ByteArray(0)
                    val nodes = BitmaskUtils.decompressBitmaskToUByteSet(nodeBitmap)
                    val chipType = inputStream.getByte()
                    val chipVersion = inputStream.getByte()
                    Response(
                        serialApiVersion,
                        capabilities,
                        nodes,
                        chipType,
                        chipVersion
                    )
                }
            }
    }

    class Request : FunctionRequest(FunctionID.SERIAL_API_GET_INIT_DATA)

    class Response(
        val serialApiVersion: Byte,
        val capabilities: Capabilities,
        val nodes: Set<NodeID>,
        val chipType: Byte,
        val chipVersion: Byte
    ) : FunctionResponse(FunctionID.SERIAL_API_GET_INIT_DATA) {

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
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

        override fun toString(): String =
            "${functionId}(" +
                    "ver: $serialApiVersion, " +
                    "capabilities: [$capabilities], " +
                    "chip type/version: $chipType/$chipVersion, " +
                    "nodes: $nodes)"

    }

}