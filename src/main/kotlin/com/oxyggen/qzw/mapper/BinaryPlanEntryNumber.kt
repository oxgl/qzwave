package com.oxyggen.qzw.mapper

class BinaryPlanEntryNumber(
    name: String,
    val type: BinaryPlanEntryNumber.Type,
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

    override fun getByteLength(context: SerializationContext): Int = context.buffer.getOrPut("${name}.byteLength") {
        if (isSuitableForVersion(context) && getOrEvaluateBoolean(context, enabled) == true) when (type) {
            Type.BYTE -> 1
            Type.INT16 -> 2
            Type.INT24 -> 3
            Type.INT32 -> 4
        } else {
            0
        }
    } as Int

    override fun getOrEvaluateValue(context: SerializationContext, expression: String): Any? =
        when (expression[0]) {
            // Has prefix
            PREFIX_POINTER, PREFIX_VIRTUAL -> {
                val name = nameWithoutPrefix(expression)
                // 1st -> check it's in context
                if (name == pureName) {
                    context.values.getOrPut(name) {
                        // 2nd -> check whether it should be determined by current entry
                        if (getByteLength(context) > 0) {
                            var result: Int = 0
                            for (index in getByteIndexRange(context)) {
                                result = result * 256 + context.binary[index]
                            }
                            when (type) {
                                Type.BYTE -> result.toByte()
                                else -> result
                            }
                        } else return null
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
            if (getByteLength(context) > 0) {
                context.values[pureName] = value
                var intValue = toIntValue(value) ?: 0

                val indices = getByteIndexRange(context)

                while (context.binary.size < indices.last + 1) context.binary += 0

                for (index in indices.reversed()) {
                    context.binary[index] = intValue.rem(256).toByte()
                    intValue = intValue.div(256)
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