package com.oxyggen.qzw

import com.oxyggen.qzw.driver.Driver
import com.oxyggen.qzw.driver.SerialDriver
import com.oxyggen.qzw.frame.FrameACK
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.FunctionZWGetVersion
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class SerialDriverTest {
    @Test
    fun `Device open & close test`() {
        val driver = SerialDriver("/dev/ttyACM0") as Driver
        driver.start()

        runBlocking {
            while (true) {
                val frame = driver.getFrame()
//                if (frame == null) break
                println(frame.toString())
                driver.putFrame(FrameACK())
            }
        }

        driver.stop()
    }

    @Test
    fun `Test get version`() {
        val driver = SerialDriver("/dev/ttyACM0") as Driver
        driver.start()
        runBlocking {
            val requestFrame = FunctionZWGetVersion().getRequestFrame()
            driver.putFrame(requestFrame)

            while (true) {
                val responseFrame = driver.getFrame()
                if (responseFrame is FrameSOF) {
                    println(responseFrame.toString())
                    driver.putFrame(FrameACK())
                    if (responseFrame.function is FunctionZWGetVersion) break
                }

            }

        }

        driver.stop()
    }
}