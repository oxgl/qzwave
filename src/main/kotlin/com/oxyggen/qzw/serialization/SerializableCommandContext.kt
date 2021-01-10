package com.oxyggen.qzw.serialization

import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.node.NetworkInfoGetter
import com.oxyggen.qzw.node.NodeInfo
import com.oxyggen.qzw.types.CommandClassID

open class SerializableCommandContext(
    networkInfo: NetworkInfoGetter,
    frame: FrameSOF,
    val function: Function,
    val currentNode: NodeInfo,
    val commandClassID: CommandClassID
) :
    SerializableFunctionContext(networkInfo, frame) {
    constructor(
        functionContext: SerializableFunctionContext,
        function: Function,
        currentNode: NodeInfo,
        commandClassID: CommandClassID
    ) : this(
        functionContext.networkInfo,
        functionContext.frame,
        function,
        currentNode,
        commandClassID
    )

    val commandClassVersion
        get() = currentNode.getSupportedCCVersion(commandClassID)

}
