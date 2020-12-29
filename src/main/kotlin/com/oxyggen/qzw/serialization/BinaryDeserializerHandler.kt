package com.oxyggen.qzw.serialization

import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

class BinaryDeserializerHandler<T,C: BinaryDeserializerContext>(val objectDescription: String, vararg deserClasses: KClass<*>) : Logging {
    private val deserializers: Map<Byte, BinaryDeserializer<T, C>>

    init {
        val desers = mutableMapOf<Byte, BinaryDeserializer<T, C>>()
        deserClasses.forEach { it ->
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
    private fun analyze(deserClass: KClass<*>): Map<Byte, BinaryDeserializer<T, C>>? {
        val coi = deserClass.companionObjectInstance
        return if (coi is BinaryDeserializer<*, *>) {
            val signatureBytes = coi.getHandledSignatureBytes()
            val result = mutableMapOf<Byte, BinaryDeserializer<T, C>>()
            signatureBytes.forEach {
                result[it] = coi as BinaryDeserializer<T, C>
            }
            result
        } else {
            logger.debug("Class ${deserClass.simpleName} is not a valid deserializer!")
            null
        }
    }

    fun deserialize(inputStream: InputStream, context: C): T {
        val deserializer = deserializers[context.signatureByte]
            ?: throw IOException("Unknown $objectDescription signature byte 0x%02x!".format(context.signatureByte))
        return deserializer.deserialize(inputStream, context)
    }

}