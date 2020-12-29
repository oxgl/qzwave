package com.oxyggen.qzw.function

import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinarySerializer
import org.apache.logging.log4j.kotlin.Logging
import java.io.OutputStream

abstract class Function : Logging {
    open fun serialize(outputStream: OutputStream, frame: FrameSOF) {
        TODO("Not yet implemented")
    }

    open fun getRequestFrame(): FrameSOF = FrameSOF(FrameSOF.FrameType.REQUEST, this)
}