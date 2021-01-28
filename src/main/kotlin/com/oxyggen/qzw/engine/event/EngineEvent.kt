package com.oxyggen.qzw.engine.event

import java.time.LocalDateTime

open class EngineEvent(val created: LocalDateTime = LocalDateTime.now())