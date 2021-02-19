package com.oxyggen.qzw.transport.serialization

import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.function.Function
import com.oxyggen.qzw.types.CommandClassID

open class SerializableCommandContext(
    frame: FrameSOF,
    val function: Function,
    val currentNode: Node,
    val commandClassID: CommandClassID
) :
    SerializableFunctionContext(frame) {
    constructor(
        functionContext: SerializableFunctionContext,
        function: Function,
        currentNode: Node,
        commandClassID: CommandClassID
    ) : this(
        functionContext.frame,
        function,
        currentNode,
        commandClassID
    )

    val commandClassVersion
        get() = currentNode.getSupportedCCVersion(commandClassID)

}
