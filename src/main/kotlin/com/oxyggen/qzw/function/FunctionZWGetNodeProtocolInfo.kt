package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.extensions.putUByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionZWGetNodeProtocolInfo {

    // HOST->ZW: REQ | 0x41 | bNodeID
    // ZW->HOST: RES | 0x41 | nodeInfo (see figure above)

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_GET_NODE_PROTOCOL_INFO.byteValue)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request.deserialize(inputStream.readAllBytes())
                FrameType.RESPONSE -> {
                    Response(true)
                    TODO("FINISH!")
                }
            }
    }

    class Request(val nodeID: NodeID) : FunctionRequest(FunctionID.ZW_GET_NODE_PROTOCOL_INFO) {
        companion object {
            private val mapper by lazy {
                mapper<Request> {
                    byte("nodeID")
                }
            }

            fun deserialize(byteArray: ByteArray) = mapper.deserialize<Request>(byteArray)
        }

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.write(mapper.serialize(this))
        }

    }

    class Response(val listening: Boolean) : FunctionResponse(FunctionID.ZW_GET_NODE_PROTOCOL_INFO) {
        companion object {
        }
    }

}