package com.oxyggen.qzw.mapper

import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue

class BinaryPlan : ArrayList<BinaryPlanEntry>() {

    //private val entries = mutableListOf<BinaryPlanEntry>()

    fun addSimple(
        name: String,
        type: BinaryPlanEntryNumber.Type,
        enabled: String
    ) =
        add(
            BinaryPlanEntryNumber(
                name = name,
                type = type,
                previous = lastOrNull(),
                enabled = enabled
            )
        )

    fun addSequence(
        name: String,
        count: String
    ) =
        add(
            BinaryPlanEntrySequence(
                name = name,
                previous = lastOrNull(),
                count = count
            )
        )

    fun addBitMask(
        name: String,
        enabled: String = "true",
        masks: Map<String, IntRange>
    ) = add(
        BinaryPlanEntryBitMask(
            name = name,
            previous = lastOrNull(),
            enabled = enabled,
            masks = masks
        )
    )

    fun getValue(context: SerializationContext, expression: String): Any? {
        val element = lastOrNull() ?: return null
        return element.getOrEvaluateValue(context, expression)
    }

    fun setValue(context: SerializationContext, expression: String, value: Any): Boolean {
        val element = lastOrNull() ?: return false
        return element.setValue(context, expression, value)
    }

}