package com.miruni.feature.calendar.data.dto.response

data class DailyPlanItem(
    val planType: String,
    val planId: Int,
    val title: String,
    val subTitle: String,
    val startTime: String,
    val endTime: String,
    val priority: String,
    val isDone: Boolean
)