@file:Suppress("MemberVisibilityCanBePrivate")

package com.oxyggen.qzw.transport.frame

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.engine.network.NetworkCallbackKey
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.extensions.getNBytes
import com.oxyggen.qzw.extensions.putByte
import com.oxyggen.qzw.extensions.putBytes
import com.oxyggen.qzw.transport.factory.FunctionFactory
import com.oxyggen.qzw.transport.function.Function
import com.oxyggen.qzw.transport.function.FunctionRequest
import com.oxyggen.qzw.transport.function.FunctionResponse
import com.oxyggen.qzw.transport.serialization.BinaryFrameDeserializer
import com.oxyggen.qzw.transport.serialization.DeserializableFrameContext
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import com.oxyggen.qzw.transport.serialization.SerializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.NodeID
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.experimental.xor

open class FrameSOF(network: Network, val function: Function, predecessor: Frame? = null) :
    Frame(network, predecessor) {

    companion object : BinaryFrameDeserializer {
        const val SIGNATURE = 0x01.toByte()

        override fun getHandledSignatureBytes() = setOf(SIGNATURE)

        private fun calculateChecksum(data: ByteArray): Byte {
            var chksum = 0xff.toByte()
            data.forEach {
                chksum = chksum.xor(it)
            }
            return chksum
        }

        @ExperimentalUnsignedTypes
        override suspend fun deserialize(inputStream: InputStream, context: DeserializableFrameContext): FrameSOF {
            // First is always the length byte
            val lengthByte = inputStream.getByte()
            val length = lengthByte.toUByte().toInt()

            // Next byte is the frame type (REQ/RES)
            val frameTypeByte = inputStream.getByte()
            val frameType = FrameType.getByByteValue(frameTypeByte) ?: throw IOException("Invalid frame type byte!")


            // The value of the Length Field MUST NOT include the SOF
            // and Checksum fields.
            // On the other hand the we already removed length and type
            // byte from buffer, so we should decrease the size by 2
            val data = inputStream.getNBytes(length - 2)


            // Now create data for checksum calculation
            // This buffer should contain the length field and data fields
            var dataForChecksum = ByteArray(0)
            dataForChecksum += lengthByte
            dataForChecksum += frameTypeByte
            dataForChecksum += data

            // Get checksum byte from input stream
            val checksumByte = inputStream.getByte()

            // Calculate checksum for data & compare with checksum byte received from serial
            if (calculateChecksum(dataForChecksum) != checksumByte)
                throw IOException("Invalid checksum received!")

            // Create function from data (data already contains only function data)
            val function = FunctionFactory.deserializeFunction(data.inputStream(), context, frameType)

            // Create new SOF frame
            return FrameSOF(context.network, function)
        }
    }

    val frameType = when (function) {
        is FunctionRequest -> FrameType.REQUEST
        is FunctionResponse -> FrameType.RESPONSE
        else -> FrameType.REQUEST
    }

    open fun getNetworkCallbackKey(): NetworkCallbackKey? = function.getNetworkCallbackKey(network)

    open fun getNode(): Node? =
        function.getNode(network) ?: getNetworkCallbackKey()?.let { network.getNodeByCallbackKey(it, this) }

    @ExperimentalUnsignedTypes
    override suspend fun serialize(outputStream: OutputStream, context: SerializableFrameContext) {
        var resultData = ByteArray(0)

        val functionOS = ByteArrayOutputStream()
        function.serialize(functionOS, SerializableFunctionContext(context, this))

        val functionData = functionOS.toByteArray()

        // Put size byte (function data + length + type)
        val size = functionData.size + 2

        resultData += size.toUByte().toByte()
        resultData += frameType.byteValue
        resultData += functionData

        resultData += calculateChecksum(resultData)

        outputStream.putByte(SIGNATURE)
        outputStream.putBytes(resultData)
    }

    override fun toString() = "${frameType.toString().substring(0..2)}: $function"
}

