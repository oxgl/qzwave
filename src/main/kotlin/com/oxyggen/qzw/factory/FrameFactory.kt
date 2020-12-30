package com.oxyggen.qzw.factory

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.frame.*
import com.oxyggen.qzw.serialization.BinaryFrameDeserializerContext
import com.oxyggen.qzw.serialization.BinaryDeserializerHandler
import org.apache.logging.log4j.kotlin.Logging
import java.io.InputStream

class FrameFactory {
    companion object : Logging {
        private val bdh by lazy {
            BinaryDeserializerHandler<Frame, BinaryFrameDeserializerContext>(
                objectDescription = "frame",
                FrameACK::class,
                FrameNAK::class,
                FrameCAN::class,
                FrameSOF::class
            )
        }

        fun deserializeFrame(inputStream: InputStream): Frame {
            val signatureByte = inputStream.getByte()
            val context = BinaryFrameDeserializerContext(signatureByte)
            return bdh.deserialize(inputStream, context)
        }
    }
}