package com.oxyggen.qzw.function

import com.oxyggen.qzw.frame.FrameSOF
import org.apache.logging.log4j.kotlin.Logging
import java.io.OutputStream

abstract class Function : Logging {
    open fun serialize(outputStream: OutputStream, frame: FrameSOF) {
        TODO("Not yet implemented")
    }

    open fun getFrame(): FrameSOF = FrameSOF(this)
}