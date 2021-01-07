package com.oxyggen.qzw.mapper

import com.oxyggen.qzw.utils.Conversion
import java.io.InvalidClassException
import java.lang.reflect.Modifier
import javax.swing.text.Utilities
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaGetter

class BinaryMapper<T> {

    val plan = BinaryPlan()

    fun byte(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.BYTE
        )
    }

    fun int16(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.INT16
        )
    }

    fun int24(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.INT24
        )
    }

    fun int32(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.INT32
        )
    }

    fun bitMap(init: BinaryMapperBitMask.() -> Unit): BinaryMapperBitMask {
        val bitMask = BinaryMapperBitMask()
        bitMask.init()
        plan.addBitMask(
            name = bitMask.collectNames(),
            enabled = "true",
            masks = bitMask.masks
        )
        return bitMask
    }

    fun byteCol(name: String, length: String) {
        plan.addSequence(
            name = name,
            count = length
        )
    }

    fun string(name: String, length: String) {
        plan.addSequence(
            name = name,
            count = length
        )
    }


    inline fun <reified T> deserialize(byteArray: ByteArray): T {
        val resultClass = T::class as KClass<*>
        val constructor = resultClass.primaryConstructor
        val params = constructor?.parameters ?: throw InvalidClassException(resultClass.qualifiedName)
        val paramValues = mutableMapOf<KParameter, Any?>()

        val context = SerializationContext(byteArray.toMutableList())
        params.forEach { param ->
            if (param.name != null) {
                val result = plan.getValue(context, "@${param.name}")
                // Try to do some conversion
                if (result != null && param.type.classifier is KClass<*>) {
                    when (param.type.classifier) {
                        Byte::class -> paramValues[param] = Conversion.toByte(result)
                        Int::class -> paramValues[param] = Conversion.toInt(result)
                        String::class -> paramValues[param] = Conversion.toString(result)
                        Boolean::class -> paramValues[param] = Conversion.toBoolean(result)
                        else -> paramValues[param] = result
                    }
                } else {
                    paramValues[param] = result
                }
            }
        }

        return constructor.callBy(paramValues) as T
    }



    private fun isFieldAccessible(property: KProperty1<*, *>): Boolean {
        return property.javaGetter?.modifiers?.let { !Modifier.isPrivate(it) } ?: false
    }

    fun serialize(source: Any): ByteArray {
        val constructor = source::class.primaryConstructor
        val params = constructor?.parameters ?: throw InvalidClassException(source::class.qualifiedName)

        val context = SerializationContext()

        val properties = source::class.memberProperties.filter { it.visibility == KVisibility.PUBLIC }

        params.forEach { param ->
            val property = properties.find { it.name == param.name }
            if (property != null) {
                val value = property.getter.call(source)
                if (value != null) plan.setValue(context, "@${property.name}", value)
            }
        }

        return context.binary.toByteArray()
    }
}


fun <T> mapper(init: BinaryMapper<T>.() -> Unit): BinaryMapper<T> {
    val binaryMapper = BinaryMapper<T>()
    binaryMapper.init()
    return binaryMapper
}