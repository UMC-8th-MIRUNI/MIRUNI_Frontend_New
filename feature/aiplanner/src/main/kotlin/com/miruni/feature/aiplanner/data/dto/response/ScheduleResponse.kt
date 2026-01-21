package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.Plan

data class ScheduleResponse(
    val plan: PlanDto,
    val progressPercentage: Int, // 진행률
) {
    fun toDomain(): Plan {
        return Plan(
            planId = plan.planId,
            title = plan.title,
            deadline = plan.deadline,
            taskRange = plan.taskRange,
            priority = plan.priority,
            progressRate = progressPercentage.toLong(),
            aiPlans = plan.aiPlans.map { it.toDomain() }
        )
    }
}