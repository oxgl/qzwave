package com.oxyggen.qzw.driver

import com.fazecast.jSerialComm.SerialPort
import com.oxyggen.qzw.factory.FrameFactory
import com.oxyggen.qzw.frame.Frame
import kotlinx.coroutines.delay
import org.apache.logging.log4j.kotlin.Logging

open class SerialDriver(private val device: String) : Driver, Logging {
    private val port = SerialPort.getCommPort(device)

    override val started: Boolean
        get() = port.isOpen

    override fun start(): Boolean {
        if (!started && port.openPort()) {
            port.setBaudRate(115200)
            port.setNumDataBits(8)
            port.setNumStopBits(1)
            port.setParity(0)
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        }
        return started
    }

    override fun stop() {
        if (started) {
            port.inputStream.close()
            port.closePort()
        }
    }

    protected suspend fun getSingleByte(): Byte {
        while (port.bytesAvailable() <= 0) {
            delay(10)
        }
        var buffer = ByteArray(1)
        port.readBytes(buffer, 1)
        return buffer[0]
    }

    override suspend fun getFrame(): Frame? {
        if (!started) return null
        try {
            return FrameFactory.deserializeFrame(port.inputStream)
        } catch (e: Throwable) {
            logger.debug(e)
            return null
        }
    }

    override fun putFrame(frame: Frame) {
        if (started) {
            try {
                frame.serialize(port.outputStream)
            } catch (e: Throwable) {
                logger.debug(e)
            }
        }
    }


    override fun toString(): String {
        return super.toString() + " device: ${device}, is opened: ${started}"
    }


}