package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.function.FunctionZWSendData
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.*
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class Command(val commandClassID: CommandClassID, val commandId: CommandID) {

    open fun getSendDataFunctionRequest(
        nodeId: NodeID,
        txOptions: TransmitOptions = TransmitOptions(),
        callbackID: FunctionCallbackID? = null
    ) = FunctionZWSendData.Request(
        nodeID = nodeId,
        command = this,
        txOptions = txOptions,
        callbackID = callbackID
    )

    open fun getSendDataFrame(
        nodeId: NodeID,
        txOptions: TransmitOptions = TransmitOptions(),
        callbackID: FunctionCallbackID? = null
    ) = FrameSOF(
        getSendDataFunctionRequest(
            nodeId = nodeId,
            txOptions = txOptions,
            callbackID = callbackID
        )
    )

    open fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
        outputStream.putByte(commandClassID.byteValue)
        outputStream.putByte(commandId.byteValue)
    }

    override fun toString(): String = "CC $commandClassID - Command ${commandId}()"

}