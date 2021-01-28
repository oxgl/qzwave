package com.oxyggen.qzw.engine.driver

import com.oxyggen.qzw.transport.factory.FrameFactory
import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.engine.network.NetworkInfoGetter
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import gnu.io.NRSerialPort
import gnu.io.UnsupportedCommOperationException
import org.apache.logging.log4j.kotlin.Logging

open class NRSerialDriver(private val device: String) : Driver, Logging {
    private val port: NRSerialPort = NRSerialPort(device, 115200, 1, 8)

    override val started: Boolean
        get() = port.isConnected

    override fun start(): Boolean {
        if (!started) try {
            port.connect()
        } catch (e: UnsupportedCommOperationException) {

        }
        return started
    }

    override fun stop() {
        if (started) port.disconnect()
    }

    override fun dataAvailable(): Int = if (started) port.inputStream.available() else -1

    override suspend fun getFrame(networkInfo: NetworkInfoGetter): Frame? {
        return if (!started) {
            null
        } else {
            try {
                FrameFactory.deserializeFrame(port.inputStream, networkInfo)
            } catch (e: Throwable) {
                // Debug info only if driver was not stopped
                if (started) logger.debug(e)
                null
            }
        }
    }

    override fun putFrame(frame: Frame, networkInfo: NetworkInfoGetter) {
        if (started) {
            try {
                frame.serialize(port.outputStream, SerializableFrameContext(networkInfo))
            } catch (e: Throwable) {
                logger.debug(e)
            }
        }
    }

    override fun toString(): String {
        return super.toString() + " device: ${device}, is opened: $started"
    }
}