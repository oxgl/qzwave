package com.oxyggen.qzw.engine.network

import com.oxyggen.qzw.engine.channel.*
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
        if (loopJob?.isActive?.not() == true)
            loopJob = coroutineScope.launch { loop(this) }
    }

    suspend fun stop() = loopJobMutex.withLock {
        loopJob?.cancelAndJoin()
        loopJob = null
    }

    private suspend fun loop(coroutineScope: CoroutineScope) {
        try {
            logger.debug { "Node $node scheduler: started" }
            while (true) {


            }
        } catch (e: CancellationException) {

        } finally {
            logger.debug { "Network $node scheduler: stopped" }
        }
    }
}