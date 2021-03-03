package com.oxyggen.qzw.engine.scheduler

import com.oxyggen.qzw.engine.channel.*
import com.oxyggen.qzw.engine.network.Node
import com.oxyggen.qzw.transport.frame.Frame
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
            logger.info { "$node scheduler started" }
            var frameAwaitingResult: Frame? = null
            while (true) {
                // Create endpoint list for select
                val eps = mutableListOf<FrameDuplexPriorityChannelEndpoint>()
                // Always add ZW
                eps.add(epZW)
                // Receive data from software also if no frame is waiting for result
                if (frameAwaitingResult == null)
                    eps.add(epSW)

                val (ep, frame) = framePrioritySelect(*eps.toTypedArray())
                when (ep) {
                    epSW -> {
                        // Frame received from software, sending to driver
                        if (frame.isAwaitingResult()) {
                            logger.debug { "$node: Frame received from ${ep.remoteEndpoint}, sending to driver. Frame is waiting for result, suspending node scheduler. Frame $frame" }
                            // Save frame awaiting result
                            frameAwaitingResult = frame
                        } else {
                            logger.debug { "$node: Frame received from ${ep.remoteEndpoint}, sending to driver. Frame $frame" }
                        }
                        epZW.send(frame)
                    }
                    epZW -> {
                        // Frame received from driver, sending back to software
                        if (frameAwaitingResult?.isPredecessorOf(frame) == true) {
                            // If the received frame is successor of frame awaiting result, reset
                            logger.debug { "$node: Frame received from ${ep.remoteEndpoint}, informing software. Resuming node scheduler. Frame sequence ${frame.toStringWithPredecessor()}" }
                            // Remove frame awaiting result
                            frameAwaitingResult = null
                        } else {
                            logger.debug { "$node: Frame received from ${ep.remoteEndpoint}, informing software. Frame sequence ${frame.toStringWithPredecessor()}" }
                        }
                        epSW.send(frame)
                    }
                }

            }
        } catch (e: CancellationException) {

        } finally {
            logger.info { "$node scheduler stopped" }
        }
    }
}