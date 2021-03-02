package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.types.FunctionID

abstract class FunctionRequest(functionID: FunctionID) : Function(functionID) {
    open fun isAwaitingResult(network: Network): Boolean = getNode(network) != null
    open fun isAwaitedResult(network: Network, functionResponse: FunctionResponse): Boolean =
        functionResponse.functionID == functionID
}