package com.oxyggen.qzw.function

import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import java.io.InputStream

abstract class FunctionZWSendData {

    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_SEND_DATA.byteValue)

        // HOST->ZW: REQ | 0x13 | nodeID | dataLength | pData[ ] | txOptions | funcID
        // ZW->HOST: RES | 0x13 | RetVal
        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> Request(1)
                FrameType.RESPONSE -> Response(1)
            }
    }

    class Request(val callbackFunction:Byte) : FunctionRequest(FunctionID.ZW_SEND_DATA)

    class Response(val callbackFunction:Byte) : FunctionResponse(FunctionID.ZW_SEND_DATA)

}