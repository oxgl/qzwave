package com.oxyggen.qzw.mapper

import com.oxyggen.qzw.utils.Conversion

abstract class BinaryPlanEntry(
    val name: String,
    val version: IntRange = IntRange.EMPTY,
    val previous: BinaryPlanEntry? = null,
) {

    companion object {
        const val PREFIX_VIRTUAL: Char = '#'
        const val PREFIX_POINTER: Char = '@'

        fun nameWithoutPrefix(name: String) =
            if (name.startsWith(PREFIX_VIRTUAL) || name.startsWith(PREFIX_POINTER)) name.drop(1) else name

        fun isPointer(name: String) = name.startsWith(PREFIX_POINTER)
    }

    protected fun isSuitableForVersion(context: SerializationContext) =
        version == IntRange.EMPTY || version.contains(context.version)

    private fun getEnabledPrevious(context: SerializationContext): BinaryPlanEntry? {
        var enabledPrev = previous
        while (enabledPrev != null && enabledPrev.getByteIndexRange(context) == IntRange.EMPTY) enabledPrev =
            enabledPrev.previous
        return enabledPrev
    }

    abstract fun isEnabled(context: SerializationContext): Boolean

    abstract fun getByteLength(context: SerializationContext): Int

    open fun getByteIndexStart(context: SerializationContext): Int =
        context.buffer.getOrPut("${name}.byteIndexStart") {
            val enabledPrev = getEnabledPrevious(context)
            enabledPrev?.getByteIndexRange(context)?.last?.plus(1) ?: 0
        } as Int

    open fun getByteIndexRange(context: SerializationContext): IntRange =
        context.buffer.getOrPut("${name}.byteIndexRange") {
            if (isEnabled(context)) {
                val startIndex = getByteIndexStart(context)

                val length = getByteLength(context)

                startIndex until (startIndex + length)
            } else {
                IntRange.EMPTY
            }
        } as IntRange

    val pureName = nameWithoutPrefix(name)

    val isVirtual = name.startsWith(PREFIX_VIRTUAL)

    abstract fun getOrEvaluateValue(context: SerializationContext, expression: String): Any?

    open fun getOrEvaluateNumber(context: SerializationContext, expression: String): Int? =
        Conversion.toInt(getOrEvaluateValue(context, expression) ?: 0)

    open fun getOrEvaluateBoolean(context: SerializationContext, expression: String): Boolean? =
        Conversion.toBoolean(getOrEvaluateValue(context, expression) ?: false)

    open fun getOrEvaluateString(context: SerializationContext, expression: String): String? =
        Conversion.toString(getOrEvaluateValue(context, expression) ?: "")

    open fun getOrEvaluateCollection(context: SerializationContext, expression: String): Collection<Any> =
        Conversion.toCollection(getOrEvaluateValue(context, expression) ?: listOf<Any>())

    abstract fun setValue(context: SerializationContext, expression: String, value: Any): Boolean

    abstract fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry

}