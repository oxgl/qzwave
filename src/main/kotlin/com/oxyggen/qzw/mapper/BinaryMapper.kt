package com.oxyggen.qzw.mapper

import java.io.InvalidClassException
import java.lang.reflect.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaGetter

class BinaryMapper {

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


    fun deserialize(byteArray: ByteArray, resultClass: KClass<*>): Any? {
        val constructor = resultClass.primaryConstructor
        val params = constructor?.parameters ?: throw InvalidClassException(resultClass.qualifiedName)
        val paramValues = mutableMapOf<KParameter, Any?>()

        val context = SerializationContext(byteArray.toMutableList())
        params.forEach {
            if (it.name != null) {
                val result = plan.getValue(context, "@${it.name}")
                paramValues[it] = result
            }
        }

        return constructor.callBy(paramValues)
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


fun mapper(init: BinaryMapper.() -> Unit): BinaryMapper {
    val binaryMapper = BinaryMapper()
    binaryMapper.init()
    return binaryMapper
}