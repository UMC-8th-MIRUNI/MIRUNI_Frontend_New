package com.miruni.feature.calendar.data.dto.response

data class GetMonthlyPlansResponse(
    val date: String, // yyyy-MM-dd
    val unfinishedPlanCount: Int
)
