package com.miruni.feature.aiplanner.domain.model

enum class PlanPriority {
    HIGH,
    MEDIUM,
    LOW;

    companion object {
        /** String -> ENUM */
        fun from(value: String?): PlanPriority = values().find {
            it.name.equals(value, ignoreCase = true)
        } ?: HIGH
    }
}