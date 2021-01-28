package com.oxyggen.qzw.transport.mapper

import com.oxyggen.qzw.utils.Conversion

class BinaryPlanEntryNumber(
    name: String,
    val type: Type,
    version: IntRange = IntRange.EMPTY,
    previous: BinaryPlanEntry? = null,
    val enabled: String,
) : BinaryPlanEntry(name, version, previous) {
    override fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry = BinaryPlanEntryNumber(
        name = this.name,
        type = this.type,
        previous = previous,
        enabled = this.enabled
    )

    enum class Type {
        BYTE,
        INT16,
        INT24,
        INT32;
    }

    override fun isEnabled(context: SerializationContext): Boolean = context.buffer.getOrPut("${name}.isEnabled") {
        isSuitableForVersion(context) && getOrEvaluateBoolean(context, enabled) == true
    } as Boolean

    override fun getByteLength(context: SerializationContext): Int = context.buffer.getOrPut("${name}.byteLength") {
        when (type) {
            Type.BYTE -> 1
            Type.INT16 -> 2
            Type.INT24 -> 3
            Type.INT32 -> 4
        }
    } as Int

    override fun getOrEvaluateValue(context: SerializationContext, expression: String): Any? =
        when (expression[0]) {
            // Has prefix
            PREFIX_POINTER, PREFIX_VIRTUAL -> {
                val name = nameWithoutPrefix(expression)

                // Check whether it should be determined by current entry
                if (name == pureName) {
                    // Check it's in context, if not calculate and put it into context
                    context.values.getOrPut(name) {
                        if (isEnabled(context)) {
                            var result = 0
                            for (index in getByteIndexRange(context)) {
                                result = result * 256 + context.binary[index]
                            }
                            when (type) {
                                Type.BYTE -> result.toByte()
                                else -> result
                            }
                        } else 0
                    }
                } else {
                    // 3rd -> try to determine using previous
                    previous?.getOrEvaluateValue(context, expression)
                }
            }
            // No prefix
            else -> expression
        }

    override fun setValue(context: SerializationContext, expression: String, value: Any): Boolean =
        if (nameWithoutPrefix(expression) == pureName) {
            if (isSuitableForVersion(context)) {
                context.values[pureName] = value
                var intValue = Conversion.toInt(value)

                // First set enabled field, it will be evaluated by getByteIndexRange
                if (isPointer(enabled))
                    previous?.setValue(context, enabled, true)

                // Get the range
                val indices = getByteIndexRange(context)

                // Create enough space in buffer
                while (context.binary.size < indices.last + 1) context.binary += 0

                // Fill data
                for (index in indices.reversed()) {
                    context.binary[index] = intValue.and(0xFF).toByte()
                    intValue = intValue.shr(8)
                    if (intValue == 0) break
                }

                true
            } else {
                true
            }
        } else {
            previous?.setValue(context, expression, value) ?: false
        }


}