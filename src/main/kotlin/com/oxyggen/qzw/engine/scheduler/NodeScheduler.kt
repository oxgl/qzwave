package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.engine.channel.*
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.frame.FrameACK
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.apache.logging.log4j.kotlin.Logging

class NodeScheduler(
    val parent: NetworkScheduler,
    val node: Node,
    val epSW: FrameDuplexPriorityChannelEndpoint,
    val epZW: FrameDuplexPriorityChannelEndpoint
) : Logging {
    private var loopJob: Job? = null
    private val loopJobMutex = Mutex()

    suspend fun start(coroutineScope: CoroutineScope) = loopJobMutex.withLock {
        if (loopJob?.isActive != true)
            loopJob = coroutineScope.launch { loop(this) }
    }

    suspend fun stop() = loopJobMutex.withLock {
        loopJob?.cancelAndJoin()
        loopJob = null
    }

    private suspend fun loop(coroutineScope: CoroutineScope) {
        try {
            logger.debug { "$node scheduler: started" }
            while (true) {
                val (ep, frame) = framePrioritySelect(epSW, epZW)
                when (ep) {
                    epSW -> epZW.send(frame)
                    epZW -> {
                        val frameACK = FrameACK(parent.network, frame)
                        epZW.send(frameACK)
                        epSW.send(frameACK)
                    }
                }

            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "$node scheduler: stopped" }
        }
    }
}