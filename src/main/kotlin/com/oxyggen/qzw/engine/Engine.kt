package com.oxyggen.qzw.engine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging

open class Engine(val engineConfig: EngineConfig, val coroutineScope: CoroutineScope = GlobalScope) : Logging {

    protected val engineChannel = Channel<EngineEvent>(Channel.UNLIMITED)

    protected enum class Status {
        STARTED, STOPPED, STOPPING
    }

    protected var loopResult: Deferred<Unit>? = null
    protected val loopResultMutex = Mutex()

    fun start() = coroutineScope.launch { startWithLock() }

    fun stop(gently: Boolean = true) = coroutineScope.launch { stopWithLock(gently) }

    val started: Boolean = loopResult?.isActive ?: false

    protected suspend fun startWithLock() {
        loopResultMutex.withLock {
            if (!started)
                loopResult = coroutineScope.async {
                    executionLoop()
                }
        }
    }

    protected suspend fun stopWithLock(gently: Boolean = true) {
        loopResultMutex.withLock {
            if (started)
                if (gently)
                    engineChannel.send(EngineEvent.EVENT_ABORT)
                else {
                    loopResult!!.cancel()
                    engineConfig.driver.stop()
                }
        }
    }

    protected suspend fun executionLoop(): Boolean {
        logger.debug { "Engine: Execution loop started" }
        if (engineConfig.driver.start()) {
            logger.error { "Engine: Unable to start driver!" }
            return false
        }


        var isActive = true
        while (isActive) {
            val event = engineChannel.receive()
            logger.debug { "Engine: Event received: $event" }
            when (event.type) {
                EngineEvent.Type.ABORT -> isActive = false
            }
        }
        engineConfig.driver.stop()
        logger.debug { "Engine: Execution loop stopped" }
        return true
    }
}