package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.FunctionCallbackKey
import com.oxyggen.qzw.transport.command.Command
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.extensions.putUByte
import com.oxyggen.qzw.transport.mapper.mapper
import com.oxyggen.qzw.engine.network.NodeInfo
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.transport.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
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
            context: DeserializableFunctionContext
        ): Function =
            when (context.frameType) {
                FrameType.REQUEST -> ZWRequest.deserialize(inputStream)
                FrameType.RESPONSE -> Response.deserialize(inputStream)
            }
    }

    class Request(
        val nodeID: NodeID,
        val command: Command,
        val txOptions: TransmitOptions,
        var functionCallbackID: FunctionCallbackID? = null
    ) : FunctionRequest(FunctionID.ZW_SEND_DATA) {
        companion object {
        }

        // HOST->ZW: REQ | 0x13 | nodeID | dataLength | pData[ ] | txOptions | funcID
        override fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            val currentNode = context.networkInfo.node[nodeID] ?: NodeInfo.getInitial(nodeID)

            val commandOS = ByteArrayOutputStream()
            command.serialize(commandOS, SerializableCommandContext(context, this, currentNode, command.commandClassID))
            val commandBytes = commandOS.toByteArray()

            // Send nodeID
            outputStream.putUByte(nodeID)

            // dataLength & pData[ ]
            outputStream.putUByte(commandBytes.size.toUByte())
            outputStream.write(commandBytes)

            // txOptions
            outputStream.putByte(txOptions.byteValue)

            // funcID
            functionCallbackID = functionCallbackID ?: context.networkInfo.getCurrentCallbackKey().functionCallbackID

            outputStream.putUByte(functionCallbackID ?: throw IOException("Invalid callback ID!"))
        }

        override fun isFunctionCallbackKeyRequired(): Boolean = functionCallbackID == null

        override fun getFunctionCallbackKey(): FunctionCallbackKey? =
            functionCallbackID?.let { FunctionCallbackKey(it) }

        override fun toString(): String =
            "${functionID}(nodeId = $nodeID, $command, functionCallbackID = $functionCallbackID)"

    }

    class Response(val success: Boolean) : FunctionResponse(FunctionID.ZW_SEND_DATA) {
        companion object {
            private val mapper = mapper<Response> {
                byte("success")
            }

            fun deserialize(inputStream: InputStream): Response = mapper.deserialize(inputStream.readAllBytes())
        }

        override fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.write(mapper.serialize(this))
        }

        override fun toString(): String = "${functionID}(result = $success)"
    }

    class ZWRequest(
        val functionCallbackID: FunctionCallbackID,
        val txStatus: TransmitStatus
    ) : FunctionRequest(FunctionID.ZW_SEND_DATA) {

        companion object {
            fun deserialize(inputStream: InputStream): ZWRequest {
                val functionCallbackID = inputStream.getUByte()
                val txStatusByte = inputStream.getByte()
                return ZWRequest(
                    functionCallbackID,
                    TransmitStatus.getByByteValue(txStatusByte) ?: TransmitStatus.COMPLETE_FAIL
                )
            }
        }

        override fun getFunctionCallbackKey(): FunctionCallbackKey? = FunctionCallbackKey(functionCallbackID)

        override fun toString(): String = buildParamList("functionCallbackID", functionCallbackID, "txStatus", txStatus)

    }

}