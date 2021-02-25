package com.oxyggen.qzw.transport.command

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.function.FunctionZWSendData
import com.oxyggen.qzw.transport.serialization.SerializableCommandContext
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID
import com.oxyggen.qzw.types.TransmitOptions
import java.io.OutputStream

@OptIn(ExperimentalUnsignedTypes::class)
abstract class Command(val commandClassID: CommandClassID, val commandId: CommandID) {

    open fun getSendDataFunctionRequest(
        node: Node,
        txOptions: TransmitOptions = TransmitOptions(),
    ) = FunctionZWSendData.Request(
        node = node,
        command = this,
        txOptions = txOptions,
    )

    open fun getSendDataFrame(
        network: Network,
        node: Node,
        txOptions: TransmitOptions = TransmitOptions(),
    ) = FrameSOF(
        network,
        getSendDataFunctionRequest(
            node = node,
            txOptions = txOptions,
        )
    )

    open suspend fun serialize(outputStream: OutputStream, context: SerializableCommandContext) {
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