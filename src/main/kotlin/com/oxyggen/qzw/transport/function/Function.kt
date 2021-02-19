package com.oxyggen.qzw.transport.function

import com.oxyggen.qzw.engine.network.FunctionCallbackKey
import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.extensions.put
import com.oxyggen.qzw.transport.frame.FrameSOF
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.FunctionID
import org.apache.logging.log4j.kotlin.Logging
import java.io.OutputStream

abstract class Function(val functionID: FunctionID) : Logging {
    open suspend fun serialize(outputStream: OutputStream, context: SerializableFunctionContext) {
        outputStream.put(functionID)
    }

    open fun getFrame(network: Network): FrameSOF = FrameSOF(network, this)

    open fun isFunctionCallbackKeyRequired(): Boolean = false

    open fun getFunctionCallbackKey(): FunctionCallbackKey? = null

    fun buildParamList(vararg params: Any): String {
        var result = ""
        for (i in params.indices step 2) {
            val name = params[i]
            val value = params[i + 1]
            if (result.isNotBlank()) result += ", "
            result += "$name: $value"
        }
        return "$functionID($result)"
    }

    override fun toString(): String = "${functionID}()"

}