package com.oxyggen.qzw.driver

import com.fazecast.jSerialComm.SerialPort
import com.oxyggen.qzw.factory.FrameFactory
import com.oxyggen.qzw.frame.Frame
import com.oxyggen.qzw.node.NetworkInfoGetter
import com.oxyggen.qzw.serialization.SerializableFrameContext
import org.apache.logging.log4j.kotlin.Logging

open class SerialDriver(private val device: String) : Driver, Logging {
    private val port = SerialPort.getCommPort(device)

    override val started: Boolean
        get() = port.isOpen

    override fun start(): Boolean {
        if (!started && port.openPort()) {
            port.baudRate = 115200
            port.numDataBits = 8
            port.numStopBits = 1
            port.parity = 0
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
        }
        return started
    }

    override fun stop() {
        if (started) {
            port.inputStream.close()
            port.closePort()
        }
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