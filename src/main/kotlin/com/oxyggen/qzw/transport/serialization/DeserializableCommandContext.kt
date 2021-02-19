package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

class DeserializableCommandContext(
    network: Network,
    frameID: FrameID,
    frameType: FrameType,
    functionID: FunctionID,
    val currentNode: Node,
    val commandClassID: CommandClassID
) : DeserializableFunctionContext(network, frameID, frameType, functionID) {

    override fun getSignatureByte(): Byte = commandClassID.byteValue

    constructor(
        functionContext: DeserializableFunctionContext,
        commandClassID: CommandClassID,
        currentNode: Node
    ) : this(
        functionContext.network,
        functionContext.frameID,
        functionContext.frameType,
        functionContext.functionID,
        currentNode,
        commandClassID
    )

    val commandClassVersion
        get() = currentNode.getSupportedCCVersion(commandClassID)

}
