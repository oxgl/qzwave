package com.oxyggen.qzw.transport.serialization

import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance

class DeserializableHandler<T, C : DeserializableObjectContext>(
    val objectDescription: String,
    vararg deserClasses: KClass<*>
) : Logging {

    class DeserializerDefinition<T, C : DeserializableObjectContext>(
        val signatureByte: Byte,
        val deserializerClass: KClass<*>,
        val deserializableInstance: DeserializableObject<T, C>
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
                /*logger.debug(
                    "Handler for $objectDescription with signature byte 0x%02x %s".format(
                        it.signatureByte,
                        it.deserializerClass
                    )
                )*/
                desers.add(it)
            }
        }
        deserializers = desers
    }

    @Suppress("UNCHECKED_CAST")
    private fun analyze(deserClass: KClass<*>): Set<DeserializerDefinition<T, C>>? {
        val coi = deserClass.companionObjectInstance
        return if (coi is DeserializableObject<*, *>) {
            val signatureBytes = coi.getHandledSignatureBytes()
            val result = mutableSetOf<DeserializerDefinition<T, C>>()
            signatureBytes.forEach {
                val definition = DeserializerDefinition(it, deserClass, coi as DeserializableObject<T, C>)
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
        val deserializer = deserializers.find { it.signatureByte == context.getSignatureByte() }?.deserializableInstance
            ?: throw IOException(
                "%s deserializer not found for signature byte 0x%02x!".format(
                    objectDescription.capitalize(),
                    context.getSignatureByte()
                )
            )
        return deserializer.deserialize(inputStream, context)
    }

}