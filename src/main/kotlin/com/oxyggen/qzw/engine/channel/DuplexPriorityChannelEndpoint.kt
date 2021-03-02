package com.oxyggen.qzw.engine.channel

import kotlinx.coroutines.channels.ReceiveChannel

interface DuplexPriorityChannelEndpoint<T> {

    val parent: DuplexPriorityChannel<T>

    val remoteEndpoint: DuplexPriorityChannelEndpoint<T>

    val priorities: IntRange

    suspend fun send(element: T, priority: Int? = null)

    fun offer(element: T, priority: Int? = null): Boolean

    suspend fun receive(): T

    fun getReceiveChannel(priority: Int): ReceiveChannel<T>
}