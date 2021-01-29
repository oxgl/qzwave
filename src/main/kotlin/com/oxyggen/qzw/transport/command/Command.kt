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
    ) = FunctionZWSendData.Request(
        nodeID = nodeId,
        command = this,
        txOptions = txOptions,
    )

    open fun getSendDataFrame(
        nodeId: NodeID,
        txOptions: TransmitOptions = TransmitOptions(),
    ) = FrameSOF(
        getSendDataFunctionRequest(
            nodeId = nodeId,
            txOptions = txOptions,
        )
    )

    open fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
        outputStream.putByte(commandClassID.byteValue)
        outputStream.putByte(commandId.byteValue)
    }

    override fun toString(): String = "CC $commandClassID - Command ${commandId}()"

    fun buildParamList(vararg params: Any): String {
        var result = ""
        for (i in params.indices step 2) {
            val name = params[i]
            val value = params[i + 1]
            if (result.isNotBlank()) result += ", "
            result += "$name: $value"
        }
        return "CC $commandClassID - Command ${commandId}($result)"
    }

}