package com.miruni.feature.aiplanner.domain.model

import java.time.LocalDate
import java.time.LocalTime

sealed class PlanInput {
    data class Text(val text: String) : PlanInput()
    data class Date(
        val startDate: LocalDate,
        val endDate: LocalDate,
        val startTime: LocalTime,
        val endTime: LocalTime,
    ) : PlanInput()
    data class Option(val option: String) : PlanInput()
}