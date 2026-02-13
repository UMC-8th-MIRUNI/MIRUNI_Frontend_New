package com.miruni.feature.calendar.data.dto.response

data class GetPlanResponse(
    val planType: String,
    val planId: Int,
    val title: String,
    val subTitle: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val priority: String
)
