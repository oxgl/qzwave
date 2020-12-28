package com.oxyggen.qzw

import com.oxyggen.qzw.driver.Driver
import com.oxyggen.qzw.driver.SerialDriver
import com.oxyggen.qzw.frame.FrameACK
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
}