package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.FunctionCallbackKey
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.*
import com.oxyggen.qzw.transport.command.Command
import com.oxyggen.qzw.transport.mapper.mapper
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

        override suspend fun deserialize(
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
    ) : FunctionRequest(FunctionID.ZW_SEND_DATA) {
        companion object;

        // HOST->ZW: REQ | 0x13 | nodeID | dataLength | pData[ ] | txOptions | funcID
        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            val currentNode = context.frame.network.node[nodeID] ?: Node.getInitial(nodeID)

            val commandOS = ByteArrayOutputStream()
            command.serialize(commandOS, SerializableCommandContext(context, this, currentNode, command.commandClassID))
            val commandBytes = commandOS.toByteArray()

            // Send nodeID
            outputStream.put(nodeID)

            // dataLength & pData[ ]
            outputStream.putUByte(commandBytes.size.toUByte())
            outputStream.putBytes(commandBytes)

            // txOptions
            outputStream.put(txOptions)

            // funcID
            val functionCallbackID = context.frame.network.provideCallbackKey(context.frame).functionCallbackID

            outputStream.put(functionCallbackID)
        }

        override fun toString(): String =
            "${functionID}(nodeId = $nodeID, $command)"

    }

    class Response(val success: Boolean) : FunctionResponse(FunctionID.ZW_SEND_DATA) {
        companion object {
            private val mapper = mapper<Response> {
                byte("success")
            }

            suspend fun deserialize(inputStream: InputStream): Response = mapper.deserialize(inputStream.getAllBytes())
        }

        override suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
            super.serialize(outputStream, context)
            outputStream.putBytes(mapper.serialize(this))
        }

        override fun toString(): String = "${functionID}(result = $success)"
    }

    class ZWRequest(
        val functionCallbackID: FunctionCallbackID,
        val txStatus: TransmitStatus
    ) : FunctionRequest(FunctionID.ZW_SEND_DATA) {

        companion object {
            suspend fun deserialize(inputStream: InputStream): ZWRequest {
                val functionCallbackID = FunctionCallbackID.getByByteValue(inputStream.getByte())
                val txStatusByte = inputStream.getByte()
                return ZWRequest(
                    functionCallbackID,
                    TransmitStatus.getByByteValue(txStatusByte) ?: TransmitStatus.COMPLETE_FAIL
                )
            }
        }

        override fun getFunctionCallbackKey(): FunctionCallbackKey = FunctionCallbackKey(functionCallbackID)

        override fun toString(): String = buildParamList("functionCallbackID", functionCallbackID, "txStatus", txStatus)

    }

}