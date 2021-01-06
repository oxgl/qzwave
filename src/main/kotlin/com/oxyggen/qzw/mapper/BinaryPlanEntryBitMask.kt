package com.oxyggen.qzw.mapper

import com.oxyggen.qzw.extensions.getBitRange
import com.oxyggen.qzw.extensions.withBitRange

class BinaryPlanEntryBitMask(
    name: String,
    version: IntRange = IntRange.EMPTY,
    previous: BinaryPlanEntry? = null,
    val enabled: String = "true",
    val masks: Map<String, IntRange>
) : BinaryPlanEntry(name, version, previous) {
    override fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry = BinaryPlanEntryBitMask(
        name = this.name,
        previous = previous,
        enabled = this.enabled,
        masks = this.masks.toMap()  // Create copy
    )

    override fun isEnabled(context: SerializationContext): Boolean = context.buffer.getOrPut("${name}.isEnabled") {
        isSuitableForVersion(context) && getOrEvaluateBoolean(context, enabled) == true
    } as Boolean

    override fun getByteLength(context: SerializationContext): Int = context.buffer.getOrPut("${name}.byteLength") {
        1
    } as Int

    override fun getOrEvaluateValue(context: SerializationContext, expression: String): Any? {
        when (expression[0]) {
            // Prefix
            PREFIX_POINTER, PREFIX_VIRTUAL -> {
                val name = nameWithoutPrefix(expression)
                // 1st -> check it's in context
                if (context.values.containsKey(name))
                    return context.values[name]

                // 2nd evaluate all sub-values
                val mask = masks.entries.find { nameWithoutPrefix(it.key) == name }
                if (mask != null) {
                    val byte = context.binary[getByteIndexRange(context).first]

                    masks.forEach {
                        val key = nameWithoutPrefix(it.key)
                        val value = byte.getBitRange(it.value)
                        context.values[key] = value
                    }

                    return context.values[name]
                }

                // 3rd -> try to determine using previous
                return previous?.getOrEvaluateValue(context, expression)
            }
            // No prefix
            else -> return expression
        }
    }

    override fun setValue(context: SerializationContext, expression: String, value: Any): Boolean {

        val name = nameWithoutPrefix(expression)

        // 1st -> check whether the name is handled by current entry
        val mask = masks.entries.find { nameWithoutPrefix(it.key) == name }

        if (mask != null) {
            return if (isSuitableForVersion(context)) {
                val index = getByteIndexStart(context)
                val intValue = toIntValue(value) ?: 0

                // First set enabled field, it will be evaluated by getByteIndexRange
                if (isPointer(enabled))
                    previous?.setValue(context, enabled, true)

                // Create enough space in buffer
                while (context.binary.size < index + 1) context.binary += 0

                // Fill data
                context.binary[index] = context.binary[index].withBitRange(mask.value, intValue.toByte())

                true
            } else {
                true
            }
        }

        return previous?.setValue(context, expression, value) ?: false
    }

}