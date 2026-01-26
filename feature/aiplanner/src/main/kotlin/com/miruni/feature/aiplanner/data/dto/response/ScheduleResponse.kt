package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.model.PlanPriority

data class ScheduleResponse(
    val planId: Long, // 상위 일정 id
    val title: String, // 상위 일정 제목
    val deadline: String, // 마감 기한
    val taskRange: String, // 일정 범위
    val priority: String, // 우선 순위
    val progressPercentage: Int, // 진행률
    val aiPlans: List<AiPlanDto> // 매칭되는 하위 일정
) {
    fun toDomain(): Plan {
        return Plan(
            planId = planId,
            title = title,
            deadline = deadline,
            taskRange = taskRange,
            priority = PlanPriority.fromServer(priority),
            progressRate = progressPercentage.toLong(),
            aiPlans = aiPlans.map { it.toDomain() }
        )
    }
}