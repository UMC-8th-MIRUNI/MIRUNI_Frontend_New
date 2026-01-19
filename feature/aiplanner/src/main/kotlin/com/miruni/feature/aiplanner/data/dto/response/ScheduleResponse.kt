package com.miruni.feature.aiplanner.data.dto.response

data class ScheduleResponse(
    val plan: PlanDto,
    val progressPercentage: Int, // 진행률
)