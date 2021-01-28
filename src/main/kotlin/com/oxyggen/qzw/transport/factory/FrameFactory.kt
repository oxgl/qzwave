package com.oxyggen.qzw.transport.factory

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.transport.frame.*
import com.oxyggen.qzw.engine.network.NetworkInfoGetter
import com.oxyggen.qzw.transport.serialization.DeserializableFrameContext
import com.oxyggen.qzw.transport.serialization.DeserializableHandler
import com.oxyggen.qzw.types.FrameID
import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream

class FrameFactory {
    companion object : Logging {
        private val bdh by lazy {
            DeserializableHandler<Frame, DeserializableFrameContext>(
                objectDescription = "frame",
                FrameACK::class,
                FrameNAK::class,
                FrameCAN::class,
                FrameSOF::class
            )
        }

        fun deserializeFrame(inputStream: InputStream, networkInfo: NetworkInfoGetter): Frame {
            val signatureByte = inputStream.getByte()
            val frameID = FrameID.getByByteValue(signatureByte) ?: throw IOException(
                "Unknown frame signature byte 0x%02x!".format(signatureByte)
            )

            val context = DeserializableFrameContext(networkInfo, frameID)

            return bdh.deserialize(inputStream, context)
        }
    }
}