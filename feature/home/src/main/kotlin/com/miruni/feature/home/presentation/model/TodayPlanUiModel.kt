package com.miruni.feature.home.presentation.model

import com.miruni.feature.home.domain.model.DailyPlan

/**
 * 오늘의 일정
 */
data class TodayPlanUiModel(
    val id: Int,
    val time: String,
    val title: String,
    val priority: String,
    val description: String
)
fun DailyPlan.toUiModel(): TodayPlanUiModel {
    return TodayPlanUiModel(
        id = planId,
        time = "$startTime - $endTime",
        title = title,
        priority = priority.ui,
        description = subTitle ?: ""
    )
}