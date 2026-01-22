package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.AiPlan
import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.model.PlanPriority

data class PostAiPlansResponse(
    val planId: Long,
    val aiPlanId: Long,
    val title: String,
    /** yyyy-MM-dd */
    val deadline: String,
    val taskRange: String,
    val priority: String,
    /** yyyy-MM-dd */
    val scheduledDate: String,
    val description: String,
    val expectedDuration: Int,
    /** hh:mm:ss */
    val startTime: String,
    /** hh:mm:ss */
    val endTime: String
) {
    fun toDomain(): Plan {
        return Plan(
            planId = planId,
            title = title,
            deadline = deadline,
            taskRange = taskRange,
            priority = PlanPriority.fromServer(priority),
            aiPlans = listOf(
                AiPlan(
                    aiPlanId = aiPlanId,
                    scheduledDate = scheduledDate,
                    startTime = startTime,
                    endTime = endTime,
                    subTitle = description,
                    expectedDuration = expectedDuration,
                )
            )
        )
    }
}