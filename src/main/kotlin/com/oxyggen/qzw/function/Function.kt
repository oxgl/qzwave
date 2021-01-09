package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.types.FunctionID
import org.apache.logging.log4j.kotlin.Logging
import java.io.OutputStream

abstract class Function(val functionID: FunctionID) : Logging {
    open fun serialize(outputStream: OutputStream, frame: FrameSOF) {
        outputStream.putByte(functionID.byteValue)
    }

    open fun getFrame(): FrameSOF = FrameSOF(this)

    override fun toString(): String = "${functionID}()"
}