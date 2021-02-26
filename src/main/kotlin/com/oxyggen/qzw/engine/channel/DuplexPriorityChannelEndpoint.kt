package com.oxyggen.qzw.engine.channel

import kotlinx.coroutines.channels.ReceiveChannel

interface DuplexPriorityChannelEndpoint<T> {
    val parent: DuplexPriorityChannel<T>

    val priorities: IntRange

    suspend fun send(element: T)

    fun offer(element: T): Boolean

    suspend fun receive(): T

    fun getReceiveChannel(priority: Int): ReceiveChannel<T>
}