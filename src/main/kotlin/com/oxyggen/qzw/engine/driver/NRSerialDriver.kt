package com.oxyggen.qzw.engine.driver

import com.oxyggen.qzw.engine.network.Network
import com.oxyggen.qzw.transport.factory.FrameFactory
import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.serialization.SerializableFrameContext
import gnu.io.NRSerialPort
import org.apache.logging.log4j.kotlin.Logging

open class NRSerialDriver(private val device: String) : Driver, Logging {
    private val port: NRSerialPort = NRSerialPort(device, 115200, 1, 8)

    override val started: Boolean
        get() = port.isConnected

    override fun start(): Boolean {
        if (!started) try {
            port.connect()
        } catch (e: Exception) {

        }
        return started
    }

    override fun stop() {
        if (started) port.disconnect()
    }

    override fun dataAvailable(): Int = if (started) port.inputStream.available() else -1

    override suspend fun getFrame(network: Network): Frame? {
        return if (!started) {
            null
        } else {
            try {
                FrameFactory.deserializeFrame(port.inputStream, network)
            } catch (e: Throwable) {
                // Debug info only if driver was not stopped
                if (started) logger.debug(e)
                null
            }
        }
    }

    override suspend fun putFrame(frame: Frame) {
        if (started) {
            try {
                frame.serialize(port.outputStream, SerializableFrameContext())
            } catch (e: Throwable) {
                logger.debug(e)
            }
        }
    }

    override fun toString(): String {
        return super.toString() + " device: ${device}, is opened: $started"
    }
}