package com.oxyggen.qzw.mapper

class BinaryPlanEntrySequence(
    name: String,
    version: IntRange = IntRange.EMPTY,
    previous: BinaryPlanEntry? = null,
    val count: String,
) : BinaryPlanEntry(name, version, previous) {
    override fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry = BinaryPlanEntrySequence(
        name = this.name,
        previous = previous,
        count = this.count
    )

    override fun getByteLength(context: SerializationContext): Int = context.buffer.getOrPut("${name}.byteLength") {
        return if (isSuitableForVersion(context)) getOrEvaluateNumber(context, count) ?: 0 else 0
    } as Int

    override fun getOrEvaluateValue(context: SerializationContext, expression: String): Any? = when (expression[0]) {
        // Has prefix
        PREFIX_POINTER, PREFIX_VIRTUAL -> {
            val name = nameWithoutPrefix(expression)
            // 1st -> check it's in context
            if (name == pureName) {
                context.values.getOrPut(name) {
                    // 2nd -> check whether it should be determined by current
                    val range = getByteIndexRange(context)
                    if (range == IntRange.EMPTY) return ByteArray(0)
                    else context.binary.toByteArray().sliceArray(range)
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

                var collection = toCollectionValue(value)
                val length = collection.size

                // Set length field
                previous?.setValue(context, "${count}", length)

                // Set bytes
                var index = getByteIndexStart(context)

                while (context.binary.size < index + length) context.binary += 0

                collection.forEach {
                    context.binary[index] = toIntValue(it)?.toByte() ?: 0
                    index++
                }

                true
            } else {
                true
            }
        } else {
            previous?.setValue(context, expression, value) ?: false
        }

}