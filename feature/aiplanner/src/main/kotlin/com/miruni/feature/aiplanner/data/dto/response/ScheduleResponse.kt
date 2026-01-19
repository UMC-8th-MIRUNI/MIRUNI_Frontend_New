package com.miruni.feature.aiplanner.data.dto.response

data class ScheduleResponse(
    val plan: PlanModel,
    val progressPercentage: Int, // 진행률
)