package com.oxyggen.qzw

import com.oxyggen.qzw.engine.driver.Driver
import com.oxyggen.qzw.engine.driver.NRSerialDriver
import com.oxyggen.qzw.engine.Engine
import com.oxyggen.qzw.engine.config.EngineConfig
import com.oxyggen.qzw.transport.function.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.*

internal class SerialDriverTest : Logging {

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
            val e = Engine(EngineConfig(NRSerialDriver("/dev/ttyACM0")))

            e.start()
            logger.debug { "Test: Engine started" }

            e.sendFrame(FunctionSerialApiGetInitData.Request().getFrame())

            /*for (i in 2..10) {
                val fNPI = FunctionZWGetNodeProtocolInfo.Request(i.toUByte()).getFrame()
                e.sendFrame(fNPI)
                val f =
                    CCVersion.Get().getSendDataFrame(i.toUByte())
//                e.sendFrame(f)
                delay(4000)
            }*/

            delay((5_000))
            e.sendFrame(FunctionSerialApiGetInitData.Request().getFrame())

            delay(60_000)
            logger.debug { "Test: Stopping engine" }
            e.stopAndWait()
            logger.debug { "Test: Engine stopped" }
        }
    }
}