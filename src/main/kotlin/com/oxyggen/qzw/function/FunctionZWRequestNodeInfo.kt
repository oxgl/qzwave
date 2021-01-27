package com.oxyggen.qzw.function

import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.serialization.SerializableFunctionContext
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
        // HOST->ZW: REQ | 0x60 | NodeID
        // ZW->HOST: RES | 0x60 | retVal

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_REQUEST_NODE_INFO.byteValue)

        override fun deserialize(
            inputStream: InputStream,
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    class Request(val nodeID: NodeID) : FunctionRequest(FunctionID.ZW_REQUEST_NODE_INFO) {
        companion object {
            private val mapper by lazy {
                mapper<FunctionZWGetNodeProtocolInfo.Request> {
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

    class Response(val retVal: Boolean) : FunctionResponse(FunctionID.ZW_REQUEST_NODE_INFO) {
        companion object {
            private val mapper by lazy {
                mapper<FunctionZWGetNodeProtocolInfo.Request> {
                    byte("retVal")
                }
            }

            fun deserialize(inputStream: InputStream) = mapper.deserialize<Response>(inputStream.readAllBytes())
        }

        override fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this))
        }
    }

}