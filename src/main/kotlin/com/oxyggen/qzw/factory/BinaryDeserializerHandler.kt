package com.oxyggen.qzw.factory

import com.oxyggen.qzw.driver.BinaryDeserializer
import com.oxyggen.qzw.extensions.getByte
import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

class BinaryDeserializerHandler<T>(val objectDescription: String, vararg deserClasses: KClass<*>) : Logging {
    private val deserializers: Map<Byte, BinaryDeserializer<T>>

    init {
        var desers = mutableMapOf<Byte, BinaryDeserializer<T>>()
        deserClasses.forEach {
            analyze(it)?.forEach {
                logger.debug(
                    "Handler for $objectDescription with signature byte 0x%02x %s".format(
                        it.key,
                        it.value::class.qualifiedName?.removeSuffix(".Companion")
                    )
                )
                desers[it.key] = it.value
            }
        }
        deserializers = desers
    }

    @Suppress("UNCHECKED_CAST")
    private fun analyze(deserClass: KClass<*>): Map<Byte, BinaryDeserializer<T>>? {
        val coi = deserClass.companionObjectInstance
        if (coi is BinaryDeserializer<*>) {
            val signatureBytes = coi.getHandledSignatureBytes()
            val result = mutableMapOf<Byte, BinaryDeserializer<T>>()
            signatureBytes.forEach {
                result.put(it, coi as BinaryDeserializer<T>)
            }
            return result
        } else {
            logger.debug("Class ${deserClass.simpleName} is not a valid deserializer!")
            return null
        }
    }

    fun deserialize(inputStream: InputStream): T {
        val signatureByte = inputStream.getByte()
        val deserializer = deserializers[signatureByte]
            ?: throw IOException("Unknown $objectDescription signature byte 0x%02x!".format(signatureByte))
        return deserializer.deserialize(signatureByte, inputStream)
    }

}