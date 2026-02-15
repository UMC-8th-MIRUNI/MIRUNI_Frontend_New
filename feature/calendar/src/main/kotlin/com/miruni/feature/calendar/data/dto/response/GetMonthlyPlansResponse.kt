package com.miruni.feature.calendar.data.dto.response

import com.miruni.feature.calendar.domain.model.Day

data class GetMonthlyPlansResponse(
    val date: String, // yyyy-MM-dd
    val unfinishedPlanCount: Int
) {
    fun toDomain(): Day {
        return Day(
            date = date,
            unfinishedPlanCount = unfinishedPlanCount
        )
    }
}