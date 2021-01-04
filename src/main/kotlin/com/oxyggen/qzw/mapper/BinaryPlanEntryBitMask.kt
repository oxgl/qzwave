package com.oxyggen.qzw.mapper

class BinaryPlanEntryBitMask(
    name: String,
    previous: BinaryPlanEntry? = null,
    val enabled: String = "true",
    val masks: Map<String, IntRange>
) : BinaryPlanEntry(name, Type.BIT_MASK, previous) {
    override fun clone(previous: BinaryPlanEntry?): BinaryPlanEntry = BinaryPlanEntryBitMask(
        name = this.name,
        previous = previous,
        enabled = this.enabled,
        masks = this.masks.toMap()  // Create copy
    )
}