package com.oxyggen.qzw.function

import com.oxyggen.qzw.driver.BinarySerializer
import com.oxyggen.qzw.driver.SerialIO
import com.oxyggen.qzw.extensions.getByte
import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class Function : Logging, BinarySerializer {
    override fun serialize(outputStream: OutputStream) {
        TODO("Not yet implemented")
    }
}