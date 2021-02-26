package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.getAllBytes
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.put
import com.oxyggen.qzw.extensions.putBytes
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class FunctionZWRequestNodeInfo {
    // This function is used to request the Node Information Frame from
    // a controller based node in the network. The Node info is retrieved
    // using the ApplicationControllerUpdate callback function with the
    // status UPDATE_STATE_NODE_INFO_RECEIVED.

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_REQUEST_NODE_INFO.byteValue)

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
     * HOST->ZW: REQ | 0x60 | NodeID
     ************************************************************************************/
    class Request(val node: Node) : FunctionRequest(FunctionID.ZW_REQUEST_NODE_INFO) {
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

    }

    /************************************************************************************
     * ZW->HOST: RES | 0x60 | retVal
     ************************************************************************************/
    class Response(val retVal: Boolean) : FunctionResponse(FunctionID.ZW_REQUEST_NODE_INFO) {
        companion object {
            private val mapper by lazy {
                mapper<FunctionZWGetNodeProtocolInfo.Request> {
                    byte("retVal")
                }
            }

            suspend fun deserialize(inputStream: InputStream, context: DeserializableFunctionContext) =
                mapper.deserialize<Response>(inputStream.getAllBytes())
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this))
        }

        override fun toString(): String = buildParamList("retVal", retVal)

    }

}