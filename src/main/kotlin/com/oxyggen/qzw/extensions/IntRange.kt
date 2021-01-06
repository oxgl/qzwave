package com.oxyggen.qzw.extensions

fun IntRange.Companion.ge(from: Int) = from..Int.MAX_VALUE

fun IntRange.Companion.le(to: Int) = Int.MIN_VALUE..to
