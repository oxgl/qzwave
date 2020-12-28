package com.oxyggen.qzw.factory

import com.oxyggen.qzw.frame.*
import org.apache.logging.log4j.kotlin.Logging
import java.io.InputStream

class FrameFactory {
    companion object : Logging {
        private val bdh by lazy {
            BinaryDeserializerHandler<Frame>(
                objectDescription = "frame",
                FrameACK::class,
                FrameNAK::class,
                FrameCAN::class,
                FrameSOF::class
            )
        }

        fun deserializeFrame(inputStream: InputStream): Frame = bdh.deserialize(inputStream)
    }
}