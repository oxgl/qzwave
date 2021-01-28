package com.oxyggen.qzw.transport.mapper

import java.nio.charset.Charset

class BinaryPlan : ArrayList<BinaryPlanEntry>() {

    //private val entries = mutableListOf<BinaryPlanEntry>()

    fun addSimple(
        name: String,
        type: BinaryPlanEntryNumber.Type,
        enabled: String,
        version: IntRange
    ) =
        add(
            BinaryPlanEntryNumber(
                name = name,
                type = type,
                previous = lastOrNull(),
                enabled = enabled,
                version = version
            )
        )

    fun addSequence(
        name: String,
        count: String,
        version: IntRange,
        charset: Charset = Charsets.UTF_8
    ) =
        add(
            BinaryPlanEntrySequence(
                name = name,
                previous = lastOrNull(),
                count = count,
                version = version,
                charset = charset
            )
        )

    fun addBitMask(
        name: String,
        enabled: String = "true",
        masks: Map<String, BinaryBitMaskDefinition>,
        version: IntRange
    ) = add(
        BinaryPlanEntryBitMask(
            name = name,
            previous = lastOrNull(),
            enabled = enabled,
            masks = masks,
            version = version
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