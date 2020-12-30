package com.oxyggen.qzw.serialization

import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

class BinaryDeserializerHandler<T, C : BinaryDeserializerContext>(
    val objectDescription: String,
    vararg deserClasses: KClass<*>
) : Logging {

    class DeserializerDefinition<T, C : BinaryDeserializerContext>(
        val signatureByte: Byte,
        val deserializerClass: KClass<*>,
        val deserializerInstance: BinaryDeserializer<T, C>
    ) {
        override fun equals(other: Any?): Boolean = if (other is DeserializerDefinition<*, *>) {
            other.signatureByte == this.signatureByte
        } else {
            false
        }

        override fun hashCode(): Int {
            return this.signatureByte.hashCode()
        }
    }

    private val deserializers: Set<DeserializerDefinition<T, C>>

    init {
        val desers = mutableSetOf<DeserializerDefinition<T, C>>()
        deserClasses.forEach { it ->
            analyze(it)?.forEach {
                logger.debug(
                    "Handler for $objectDescription with signature byte 0x%02x %s".format(
                        it.signatureByte,
                        it.deserializerClass
                    )
                )
                desers.add(it)
            }
        }
        deserializers = desers
    }

    @Suppress("UNCHECKED_CAST")
    private fun analyze(deserClass: KClass<*>): Set<DeserializerDefinition<T, C>>? {
        val coi = deserClass.companionObjectInstance
        return if (coi is BinaryDeserializer<*, *>) {
            val signatureBytes = coi.getHandledSignatureBytes()
            val result = mutableSetOf<DeserializerDefinition<T, C>>()
            signatureBytes.forEach {
                val definition = DeserializerDefinition(it, deserClass, coi as BinaryDeserializer<T, C>)
                result.add(definition)
            }
            result
        } else {
            logger.debug("Class ${deserClass.simpleName} is not a valid deserializer!")
            null
        }
    }

    fun getClassBySignatureByte(signatureByte: Byte) =
        deserializers.find { it.signatureByte == signatureByte }?.deserializerClass

    fun deserialize(inputStream: InputStream, context: C): T {
        val deserializer = deserializers.find { it.signatureByte == context.signatureByte }?.deserializerInstance
            ?: throw IOException("Unknown $objectDescription signature byte 0x%02x!".format(context.signatureByte))
        return deserializer.deserialize(inputStream, context)
    }

}