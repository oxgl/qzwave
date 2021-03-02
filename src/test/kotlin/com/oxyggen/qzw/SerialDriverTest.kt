package com.oxyggen.qzw

import com.oxyggen.qzw.engine.Engine
import com.oxyggen.qzw.engine.config.EngineConfig
import com.oxyggen.qzw.engine.driver.Driver
import com.oxyggen.qzw.engine.driver.JSerialDriver
import com.oxyggen.qzw.transport.frame.Frame
import com.oxyggen.qzw.transport.function.FunctionSerialApiGetInitData
import com.oxyggen.qzw.transport.function.FunctionZWGetNodeProtocolInfo
import com.oxyggen.qzw.types.NodeID
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.Logging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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

    fun resultCallback(frame: Frame) {
        logger.info("Result: $frame")
    }

    @Test
    fun `Device open & close test`() {
        runBlocking {
            val e = Engine(EngineConfig(JSerialDriver("/dev/ttyACM0")))

            e.start()

            logger.debug { "Test: Engine started: ${e.started}" }

            val n4 = e.getNodeByID(4)

            val fn = FunctionZWGetNodeProtocolInfo.Request(n4)
            val frame = e.sendFunction(fn)

            val fn2 = FunctionSerialApiGetInitData.Request()
            val frame2 = e.sendFunction(fn2)

            //val frame = FunctionZWRequestNodeInfo.Request(NodeID(4)).getFrame()

            //e.sendFrame(FunctionSerialApiGetInitData.Request().getFrame(), ::resultCallback)


            /*for (i in 2..10) {
                val fNPI = FunctionZWGetNodeProtocolInfo.Request(i.toUByte()).getFrame()
                e.sendFrame(fNPI)
                val f =
                    CCVersion.Get().getSendDataFrame(i.toUByte())
//                e.sendFrame(f)
                delay(4000)
            }*/

            /*delay((5_000))
            e.sendFrame(frame, ::resultCallback)

            delay((5_000))*/
/*            val frame2 = CCVersion.CommandClassGet(CommandClassID.SWITCH_MULTILEVEL)
                .getSendDataFrame(NodeID(4), TransmitOptions(requestAcknowledge = false))
            e.sendFrame(frame2, ::resultCallback)

            delay(5_000)
            val frame3 = CCVersion.CommandClassGet(CommandClassID.SWITCH_BINARY).getSendDataFrame(NodeID(4))
            e.sendFrame(frame3, ::resultCallback)*/

            //e.sendFrame(FunctionSerialApiGetInitData.Request().getFrame())

            delay(60_000)
            logger.debug { "Test: Stopping engine" }
            e.stop()
            logger.debug { "Test: Engine stopped" }
        }
    }
}