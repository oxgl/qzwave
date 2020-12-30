package com.oxyggen.qzw

import com.oxyggen.qzw.driver.Driver
import com.oxyggen.qzw.driver.SerialDriver
import com.oxyggen.qzw.frame.FrameACK
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.FunctionSerialApiGetCapabilities
import com.oxyggen.qzw.function.FunctionSerialApiGetInitData
import com.oxyggen.qzw.function.FunctionZWGetRandom
import com.oxyggen.qzw.function.FunctionZWGetVersion
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream

internal class SerialDriverTest {

    var driver:Driver? = null

    @BeforeEach
    fun `Prepare test`()
    {
        driver = SerialDriver("/dev/ttyACM0")
        driver!!.start()
    }

    @AfterEach
    fun `Shutdown test`()
    {
        driver!!.stop()
        driver = null
    }



    @Test
    fun `Device open & close test`() {

        runBlocking {
            while (true) {
                val frame = driver!!.getFrame()
//                if (frame == null) break
                println(frame.toString())
                driver!!.putFrame(FrameACK())
            }
        }
    }

    @Test
    fun `Test get version`() {
        runBlocking {
            val requestFrame = FunctionZWGetVersion.Request().getFrame()
            driver!!.putFrame(requestFrame)

            while (true) {
                val responseFrame = driver!!.getFrame()
                if (responseFrame is FrameSOF) {
                    println(responseFrame.toString())
                    driver!!.putFrame(FrameACK())
                    if (responseFrame.function is FunctionZWGetVersion) break
                }
            }
        }
    }

    @Test
    fun `Test random`() {
        runBlocking {
            val requestFun = FunctionZWGetRandom.Request(20)
            driver!!.putFunction(requestFun)

            while (true) {
                val responseFrame = driver!!.getFrame()
                if (responseFrame is FrameSOF) {
                    println(responseFrame.toString())
                    driver!!.putFrame(FrameACK())
                    if (responseFrame.function is FunctionZWGetRandom.Response) break
                }

            }
        }
    }

    @Test
    fun `Test get init data`() {
        runBlocking {
            val requestFun = FunctionSerialApiGetInitData.Request()
            driver!!.putFunction(requestFun)

            while (true) {
                val responseFrame = driver!!.getFrame()
                if (responseFrame is FrameSOF) {
                    println(responseFrame.toString())
                    driver!!.putFrame(FrameACK())
                    if (responseFrame.function is FunctionSerialApiGetInitData.Response) break
                }
            }
        }
    }

    @Test
    fun `Test get capabilities`() {
        runBlocking {
            val requestFun = FunctionSerialApiGetCapabilities.Request()
            driver!!.putFunction(requestFun)

            while (true) {
                val responseFrame = driver!!.getFrame()
                if (responseFrame is FrameSOF) {
                    println(responseFrame.toString())
                    driver!!.putFrame(FrameACK())
                    if (responseFrame.function is FunctionSerialApiGetCapabilities.Response) {
                        val b = ByteArrayOutputStream()
                        responseFrame.serialize(b)
                        val ba2 = b.toByteArray()
                        val ba1 = ba2

                        break
                    }
                }
            }
        }
    }

}