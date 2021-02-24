package com.oxyggen.qzw.engine.channel

interface DuplexPriorityChannel<T> {
    val endpointA: DuplexPriorityChannelEndpoint<T>
    val endpointB: DuplexPriorityChannelEndpoint<T>
}