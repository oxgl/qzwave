package com.oxyggen.qzw.engine

import java.time.LocalDateTime

open class EngineEvent(val created: LocalDateTime = LocalDateTime.now())