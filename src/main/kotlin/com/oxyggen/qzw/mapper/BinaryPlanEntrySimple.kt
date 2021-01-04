package com.oxyggen.qzw.mapper

class BinaryPlanEntrySimple(
    name: String,
    type: BinaryPlanEntry.Type,
    previous: BinaryPlanEntry? = null,
    val enabled: String,
) : BinaryPlanEntry(name, type, previous) {
    override fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry = BinaryPlanEntrySimple(
        name = this.name,
        type = this.type,
        previous = previous,
        enabled = this.enabled
    )
}