package com.oxyggen.qzw.mapper

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class BinaryMapper {

    val plan = BinaryPlan()

    fun byte(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntry.Type.BYTE
        )
    }

    fun int16(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntry.Type.INT16
        )
    }

    fun int24(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntry.Type.INT24
        )
    }

    fun int32(name: String, enabled: String = "true") {
        plan.addSimple(
            name = name,
            enabled = enabled,
            type = BinaryPlanEntry.Type.INT32
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
        val mutablePlan = plan.clone()

        return null
    }
}


fun mapper(init: BinaryMapper.() -> Unit): BinaryMapper {
    val binaryMapper = BinaryMapper()
    binaryMapper.init()
    return binaryMapper
}