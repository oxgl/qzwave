package com.oxyggen.qzw.mapper

abstract class BinaryPlanEntry(
    val name: String,
    val type: Type,
    val previous: BinaryPlanEntry? = null,
) : BinaryValueGetter {

    enum class Type {
        BYTE,
        INT16,
        INT24,
        INT32,
        BYTE_SEQ,
        BIT_MASK;
    }

    companion object {
        const val PREFIX_VIRTUAL: Char = '#'
        const val PREFIX_POINTER: Char = '@'
    }

    override fun getStringValue(byteArray: ByteArray, expression: String): String? =
        when (expression[0]) {
            PREFIX_VIRTUAL -> null
            PREFIX_POINTER -> {
                if (expression.drop(1) == name)
                    "theValue"
                else
                    previous?.getStringValue(byteArray, expression)
            }
            else -> expression
        }

    abstract fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry

}