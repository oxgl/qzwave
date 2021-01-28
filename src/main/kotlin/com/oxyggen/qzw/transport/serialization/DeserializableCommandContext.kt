package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.NetworkInfoGetter
import com.oxyggen.qzw.engine.network.NodeInfo
import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.FrameID
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID

class DeserializableCommandContext(
    networkInfo: NetworkInfoGetter,
    frameID: FrameID,
    frameType: FrameType,
    functionID: FunctionID,
    val currentNode: NodeInfo,
    val commandClassID: CommandClassID
) : DeserializableFunctionContext(networkInfo, frameID, frameType, functionID) {

    override fun getSignatureByte(): Byte = commandClassID.byteValue

    constructor(
        functionContext: DeserializableFunctionContext,
        commandClassID: CommandClassID,
        currentNode: NodeInfo
    ) : this(
        functionContext.networkInfo,
        functionContext.frameID,
        functionContext.frameType,
        functionContext.functionID,
        currentNode,
        commandClassID
    )

    val commandClassVersion
        get() = currentNode.getSupportedCCVersion(commandClassID)

}
