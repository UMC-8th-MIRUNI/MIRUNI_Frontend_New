package com.miruni.feature.aiplanner.domain.model

enum class PlanStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    companion object {
        /** String -> ENUM */
        fun from(value: String?): PlanStatus = values().find {
            it.name.equals(value, ignoreCase = true)
        } ?: TODO
    }
}