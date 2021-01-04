package com.oxyggen.qzw.mapper

class BinaryPlan {

    private val entries = mutableListOf<BinaryPlanEntry>()

    fun addSimple(
        name: String,
        type: BinaryPlanEntry.Type,
        enabled: String
    ) =
        entries.add(
            BinaryPlanEntrySimple(
                name = name,
                type = type,
                previous = entries.lastOrNull(),
                enabled = enabled
            )
        )

    fun addSequence(
        name: String,
        count: String
    ) =
        entries.add(
            BinaryPlanEntrySequence(
                name = name,
                previous = entries.lastOrNull(),
                count = count
            )
        )

    fun addBitMask(
        name: String,
        enabled: String = "true",
        masks: Map<String, IntRange>
    ) = entries.add(
        BinaryPlanEntryBitMask(
            name = name,
            previous = entries.lastOrNull(),
            enabled = enabled,
            masks = masks
        )
    )

    fun clone(): BinaryPlan {
        val result = BinaryPlan()
        entries.forEach {
            result.entries.add(
                it.clone(result.entries.lastOrNull())
            )
        }
        return result
    }

}