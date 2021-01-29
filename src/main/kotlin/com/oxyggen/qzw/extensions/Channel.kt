package com.oxyggen.qzw.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Channel<T>.init() {
    while (this.poll() != null) {
        /* Remove element only */
    }
}