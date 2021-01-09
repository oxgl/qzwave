package com.oxyggen.qzw

import com.oxyggen.qzw.command.CCBattery
import com.oxyggen.qzw.command.CCNotification
import com.oxyggen.qzw.command.CCSensorMultilevel
import com.oxyggen.qzw.command.CCVersion
import com.oxyggen.qzw.driver.Driver
import com.oxyggen.qzw.driver.SerialDriver
import com.oxyggen.qzw.extensions.build
import com.oxyggen.qzw.factory.FrameFactory
import com.oxyggen.qzw.factory.FunctionFactory
import com.oxyggen.qzw.frame.FrameACK
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.FunctionSerialApiGetCapabilities
import com.oxyggen.qzw.function.FunctionSerialApiGetInitData
import com.oxyggen.qzw.function.FunctionZWGetRandom
import com.oxyggen.qzw.function.FunctionZWGetVersion
import com.oxyggen.qzw.types.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream

internal class SerialDriverTest {

    var driver: Driver? = null

    @BeforeEach
    fun `Prepare test`() {
        driver = SerialDriver("/dev/ttyACM0")
        driver!!.start()
    }

    @AfterEach
    fun `Shutdown test`() {
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

    @Test
    fun `Test get version for node 2`() {
        runBlocking {
            val f = CCVersion.Get().getSendDataFrame(2u)
            driver!!.putFrame(f)

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

    @Test
    fun `Test commands`() {
/*        println(CommandClassID.getByByteValue(113, 1))
        println(CommandClassID.getByByteValue(113, 2))
        println(CommandClassID.getByByteValue(113, 3))
        println(CommandClassID.getByByteValue(113, 4))
        println(CommandClassID.getByByteValue(113, 5))
        println(CommandClassID.getByByteValue(113))*/
        /*val cc = CommandClassID.getByByteValue(113)
        val c = CommandID.getByByteValue(cc ?: CommandClassID.ASSOCIATION_COMMAND_CONFIGURATION, 0x01)
        val x = c*/
    }

    @Test
    fun `Test serdef`() {

        var data1 = ByteArray(0)
        data1 += 0x01 // alarmType1
        data1 += 0x02 // alarmTyp2
        data1 += 0x00 // res
        data1 += 0xff.toByte() // status
        data1 += 0x07.toByte() // type
        data1 += 0x08.toByte() // event
        data1 += Byte.build(false, true, false, false, false, false, false, false) // event
        data1 += 0xab.toByte()
        data1 += 0xcd.toByte()
        //data1 += 0xee.toByte()


        //val c = CCNotification.Report.deserialize(data1, 1)

        /*val data2 = if (c != null) CCNotification.Report.mapper.serialize(c) else null

        assert(data1.contentEquals(data2))*/

    }

    @Test
    fun `Test CCBattery`() {
        val r = CCBattery.Report(
            batteryLevel = 30,
            batteryChargingStatus = BatteryChargingStatus.DISCHARGING,
            rechargeable = true,
            backupBattery = false,
            overHeating = false,
            lowFluid = false,
            batteryReplaceStatus = BatteryReplaceStatus.REPLACE_SOON,
            disconnected = true,
            lowTemperature = true
        )

        val baos1 = ByteArrayOutputStream()
        r.serialize(baos1, FunctionZWGetRandom.Request(), 1)
        val b1 = baos1.toByteArray()

        val baos2 = ByteArrayOutputStream()
        r.serialize(baos2, FunctionZWGetRandom.Request(), 2)
        val b2 = baos2.toByteArray()

        val baos3 = ByteArrayOutputStream()
        r.serialize(baos3, FunctionZWGetRandom.Request(), 3)
        val b3 = baos3.toByteArray()


    }

    @Test
    fun `Test CCSensorMultilevel`() {
        /*val r = CCSensorMultilevel.Report(1, 2, 1, 4, listOf(4.3f, 45364f, -454.73f))

        val baos1 = ByteArrayOutputStream()
        r.serialize(baos1, FunctionZWGetRandom.Request(), 1)
        val b1 = baos1.toByteArray().drop(2).toByteArray()

        val r1 = CCSensorMultilevel.Report.deserialize(b1,1)
        val r2 = r1*/

    }

}