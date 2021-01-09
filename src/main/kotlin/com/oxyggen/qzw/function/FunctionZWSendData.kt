package com.oxyggen.qzw.function

import com.oxyggen.qzw.command.Command
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.extensions.putUByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.mapper.mapper
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.types.NodeID
import com.oxyggen.qzw.types.TransmitOptions
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@ExperimentalUnsignedTypes
abstract class FunctionZWSendData {

    companion object : BinaryFunctionDeserializer {

        // HOST->ZW: REQ | 0x13 | nodeID | dataLength | pData[ ] | txOptions | funcID
        // ZW->HOST: RES | 0x13 | RetVal
        // For IMA enabled targets the transmission
        // ZW->HOST: REQ | 0x13 | funcID | txStatus | wTransmitTicksMSB | wTransmitTicksLSB | bRepeaters |
        //                 rssi_values.incoming[0] | rssi_values.incoming[1] | rssi_values.incoming[2] | rssi_values.incoming[3] |
        //                 rssi_values.incoming[4] | bACKChannelNo | bLastTxChannelNo | bRouteSchemeState | repeater0 |
        //                 repeater1 | repeater2 | repeater3 | routespeed | bRouteTries | bLastFailedLink.from | bLastFailedLink.to

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.ZW_SEND_DATA.byteValue)

        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> throw IOException("FunctionZWSendData: Receiving data for IMA enabled targets not implemented the transmission")
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    class Request(
        val nodeID: NodeID,
        val command: Command,
        val txOptions: TransmitOptions,
        val callbackFunction: Byte
    ) : FunctionRequest(FunctionID.ZW_SEND_DATA) {
        companion object {
        }

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.putUByte(nodeID)

            val commandOS = ByteArrayOutputStream()
            command.serialize(commandOS, this, 1) // TODO FIX VERSION NUMBER!

            val commandBytes = commandOS.toByteArray()
            outputStream.write(commandBytes)
        }

        override fun toString(): String = "${functionID}($command)"

    }

    class Response(val success: Boolean) : FunctionResponse(FunctionID.ZW_SEND_DATA) {
        companion object {
            private val mapper = mapper<Response> {
                byte("success")
            }

            fun deserialize(inputStream: InputStream): Response = mapper.deserialize(inputStream.readAllBytes())
        }

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.write(mapper.serialize(this))
        }

        override fun toString(): String = "${functionID}(out result = $success)"
    }

}