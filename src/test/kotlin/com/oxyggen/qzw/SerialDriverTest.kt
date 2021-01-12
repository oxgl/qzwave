package com.oxyggen.qzw

import com.oxyggen.qzw.command.CCBattery
import com.oxyggen.qzw.command.CCNotification
import com.oxyggen.qzw.command.CCSensorMultilevel
import com.oxyggen.qzw.command.CCVersion
import com.oxyggen.qzw.driver.Driver
import com.oxyggen.qzw.driver.SerialDriver
import com.oxyggen.qzw.engine.Engine
import com.oxyggen.qzw.engine.EngineConfig
import com.oxyggen.qzw.extensions.build
import com.oxyggen.qzw.factory.FrameFactory
import com.oxyggen.qzw.factory.FunctionFactory
import com.oxyggen.qzw.frame.FrameACK
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.function.FunctionSerialApiGetCapabilities
import com.oxyggen.qzw.function.FunctionSerialApiGetInitData
import com.oxyggen.qzw.function.FunctionZWGetRandom
import com.oxyggen.qzw.function.FunctionZWGetVersion
import com.oxyggen.qzw.serialization.SerializableFrameContext
import com.oxyggen.qzw.types.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream

internal class SerialDriverTest {

    var driver: Driver? = null

    @BeforeEach
    fun `Prepare test`() {
        /*driver = SerialDriver("/dev/ttyACM0")
        driver!!.start()*/
    }

    @AfterEach
    fun `Shutdown test`() {
        /*driver!!.stop()
        driver = null*/
    }


    @Test
    fun `Device open & close test`() {
        runBlocking {
            val e = Engine(EngineConfig(SerialDriver("/dev/ttyAMC0")))

            e.start()
            delay(10_000)
            e.stop()
            delay(10000)

        }
    }
}