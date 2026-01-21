package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.model.PlanPriority

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
            priority = PlanPriority.fromServer(plan.priority),
            progressRate = progressPercentage.toLong(),
            aiPlans = plan.aiPlans.map { it.toDomain() }
        )
    }
}