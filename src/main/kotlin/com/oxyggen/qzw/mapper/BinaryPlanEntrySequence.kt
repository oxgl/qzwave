package com.oxyggen.qzw.mapper

class BinaryPlanEntrySequence(
    name: String,
    previous: BinaryPlanEntry? = null,
    val count: String,
) : BinaryPlanEntry(name, Type.BYTE_SEQ, previous) {
    override fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry = BinaryPlanEntrySequence(
        name = this.name,
        previous = previous,
        count = this.count
    )


}