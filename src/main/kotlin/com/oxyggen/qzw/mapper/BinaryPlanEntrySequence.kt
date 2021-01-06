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

    override fun isEnabled(context: SerializationContext): Boolean = context.buffer.getOrPut("${name}.isEnabled") {
        isSuitableForVersion(context)
    } as Boolean

    override fun getByteLength(context: SerializationContext): Int = context.buffer.getOrPut("${name}.byteLength") {
        getOrEvaluateNumber(context, count) ?: 0
    } as Int

    override fun getOrEvaluateValue(context: SerializationContext, expression: String): Any? = when (expression[0]) {
        // Has prefix
        PREFIX_POINTER, PREFIX_VIRTUAL -> {
            val name = nameWithoutPrefix(expression)
            // Check whether it should be determined by current entry
            if (name == pureName) {
                // Get buffered entry or create if does not exist
                context.values.getOrPut(name) {
                    if (isEnabled(context)) {
                        val range = getByteIndexRange(context)
                        if (range == IntRange.EMPTY)
                            ByteArray(0)
                        else
                            context.binary.toByteArray().sliceArray(range)
                    } else ByteArray(0)
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

                //
                val collection = toCollectionValue(value)
                val length = collection.size

                // First set length field, it will be evaluated by getByteIndexRange
                if (isPointer(count))
                    previous?.setValue(context, count, length)

                // Get start index
                var index = getByteIndexStart(context)

                // Create enough space in buffer
                while (context.binary.size < index + length) context.binary += 0

                // Set bytes
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