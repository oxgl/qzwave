package com.oxyggen.qzw.command

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.FunctionZWSendData
import com.oxyggen.qzw.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.types.NodeID
import com.oxyggen.qzw.types.TransmitOptions
import java.io.OutputStream

@ExperimentalUnsignedTypes
abstract class Command(val commandClassID: CommandClassID, val commandId: CommandID) {

    open fun getSendDataFunctionRequest(
        nodeId: NodeID,
        txOptions: TransmitOptions = TransmitOptions(),
        callbackFunction: Byte = 0
    ) = FunctionZWSendData.Request(
        nodeID = nodeId,
        command = this,
        txOptions = txOptions,
        callbackFunction = callbackFunction
    )

    open fun getSendDataFrame(
        nodeId: NodeID,
        txOptions: TransmitOptions = TransmitOptions(),
        callbackFunction: Byte = 0
    ) = FrameSOF(
        getSendDataFunctionRequest(
            nodeId = nodeId,
            txOptions = txOptions,
            callbackFunction = callbackFunction
        )
    )

    open fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
        outputStream.putByte(commandClassID.byteValue)
        outputStream.putByte(commandId.byteValue)
    }

    override fun toString(): String = "CC $commandClassID - Command ${commandId}()"

}