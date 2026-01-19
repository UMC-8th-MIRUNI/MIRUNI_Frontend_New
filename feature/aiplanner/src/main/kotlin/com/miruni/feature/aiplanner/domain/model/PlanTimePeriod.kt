package com.miruni.feature.aiplanner.domain.model

enum class PlanTimePeriod {
    RANDOM,
    MORNING,
    FOCUS_MORNING,
    AFTERNOON,
    EVENING,
    NIGHT,
    DAWN;

    companion object {
        /** String -> ENUM */
        fun from(value: String?): PlanTimePeriod = values().find {
            it.name.equals(value, ignoreCase = true)
        } ?: RANDOM
    }
}