package com.oxyggen.qzw.mapper

import com.oxyggen.qzw.types.ByteToType
import com.oxyggen.qzw.types.TypeToByte
import com.oxyggen.qzw.utils.Conversion
import com.sun.jdi.ByteType
import java.io.InvalidClassException
import java.lang.reflect.Modifier
import javax.swing.text.Utilities
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaGetter

class BinaryMapper<T> {

    val plan = BinaryPlan()
    private var bitMaskCounter = 0

    fun byte(name: String, enabled: String = "true", version: IntRange = IntRange.EMPTY) {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.BYTE,
            version = version
        )
    }

    fun int16(name: String, enabled: String = "true", version: IntRange = IntRange.EMPTY) {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.INT16,
            version = version
        )
    }

    fun int24(name: String, enabled: String = "true", version: IntRange = IntRange.EMPTY) {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.INT24,
            version = version
        )
    }

    fun int32(name: String, enabled: String = "true", version: IntRange = IntRange.EMPTY) {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntryNumber.Type.INT32,
            version = version
        )
    }

    fun bitMap(version: IntRange = IntRange.EMPTY, init: BinaryMapperBitMask.() -> Unit): BinaryMapperBitMask {
        val bitMask = BinaryMapperBitMask(version)
        bitMask.init()
        plan.addBitMask(
            name = "bitmask[$bitMaskCounter]",
            enabled = "true",
            masks = bitMask.masks,
            version = version
        )
        bitMaskCounter++
        return bitMask
    }

    fun byteCol(name: String, length: String, version: IntRange = IntRange.EMPTY) {
        plan.addSequence(
            name = name,
            count = length,
            version = version
        )
    }

    fun string(name: String, length: String, version: IntRange = IntRange.EMPTY) {
        plan.addSequence(
            name = name,
            count = length,
            version = version
        )
    }


    inline fun <reified T> deserialize(byteArray: ByteArray, version: Int = 1): T {
        val resultClass = T::class as KClass<*>
        val constructor = resultClass.primaryConstructor
        val params = constructor?.parameters ?: throw InvalidClassException(resultClass.qualifiedName)
        val paramValues = mutableMapOf<KParameter, Any?>()

        val context = SerializationContext(byteArray.toMutableList(), version = version)
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
                        else -> {
                            val compObj = (param.type.classifier as KClass<*>).companionObjectInstance
                            if (compObj is ByteToType<*>) {
                                val b = Conversion.toByte(result)
                                paramValues[param] = compObj.getByByteValue(b)
                            } else {
                                paramValues[param] = result
                            }
                        }
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

    fun serialize(source: Any, version: Int = 1): ByteArray {
        val constructor = source::class.primaryConstructor
        val params = constructor?.parameters ?: throw InvalidClassException(source::class.qualifiedName)

        val context = SerializationContext(version = version)

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