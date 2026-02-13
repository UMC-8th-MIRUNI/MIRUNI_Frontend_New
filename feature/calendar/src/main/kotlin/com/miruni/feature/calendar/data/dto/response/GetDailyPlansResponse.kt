package com.miruni.feature.calendar.data.dto.response

data class GetDailyPlansResponse(
    val unfinishedPlan: List<DailyPlanItem>,
    val finishedPlan: List<DailyPlanItem>
)
