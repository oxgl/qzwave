package com.oxyggen.qzw.engine.channel

import kotlinx.coroutines.channels.ReceiveChannel

interface DuplexPriorityChannelEndpoint<T> {

    val channel: DuplexPriorityChannel<T>

    val remoteEndpoint: DuplexPriorityChannelEndpoint<T>

    val priorities: Collection<Int>

    suspend fun send(element: T, priority: Int? = null)

    fun offer(element: T, priority: Int? = null): Boolean

    suspend fun receive(): T

    fun getReceiveChannel(priority: Int): ReceiveChannel<T>

    fun getPartialChannelEndpoint(
        priorityFilter: (Int) -> Boolean,
        subChannelName: String? = null
    ): DuplexPriorityChannelEndpoint<T>


    fun splitChannelEndpoint(
        belongsToFirst: (Int) -> Boolean,
        firstEndpointName: String? = null,
        secondEndpointName: String? = null): Pair<DuplexPriorityChannelEndpoint<T>, DuplexPriorityChannelEndpoint<T>>
}