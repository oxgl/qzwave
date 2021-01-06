package com.oxyggen.qzw.mapper

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

    protected fun getEnabledPrevious(context: SerializationContext): BinaryPlanEntry? {
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

    open fun toIntValue(value: Any?): Int? = if (value == null) null else when (value) {
        is Number -> value.toInt()
        is Boolean -> if (value) 1 else 0
        is String -> if (value.startsWith("0x")) value.drop(2).toInt(16) else value.toInt()
        else -> 0
    }

    open fun toBooleanValue(value: Any?): Boolean? = if (value == null) null else when (value) {
        is Boolean -> value
        is Number -> value.toInt() != 0
        is String -> value.toLowerCase().trim() == "true"
        else -> false
    }

    open fun toStringValue(value: Any?): String? = if (value == null) null else when (value) {
        is String -> value
        else -> value.toString()
    }

    @Suppress("UNCHECKED_CAST")
    open fun toCollectionValue(value: Any?): Collection<Any> = if (value == null) listOf() else when (value) {
        is Collection<*> -> value as Collection<Any>
        is ByteArray -> value.toList()
        is String -> value.toList()
        else -> listOf(value)
    }

    open fun getOrEvaluateNumber(context: SerializationContext, expression: String): Int? =
        toIntValue(getOrEvaluateValue(context, expression))

    open fun getOrEvaluateBoolean(context: SerializationContext, expression: String): Boolean? =
        toBooleanValue(getOrEvaluateValue(context, expression))

    open fun getOrEvaluateString(context: SerializationContext, expression: String): String? =
        toStringValue(getOrEvaluateValue(context, expression))

    open fun getOrEvaluateCollection(context: SerializationContext, expression: String): Collection<Any> =
        toCollectionValue(getOrEvaluateValue(context, expression))

    abstract fun setValue(context: SerializationContext, expression: String, value: Any): Boolean

    abstract fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry

}