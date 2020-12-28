package com.oxyggen.qzw.frame

import com.oxyggen.qzw.driver.BinarySerializer
import com.oxyggen.qzw.driver.SerialIO
import com.oxyggen.qzw.extensions.getByte
import java.io.IOException
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class Frame() : BinarySerializer {

}