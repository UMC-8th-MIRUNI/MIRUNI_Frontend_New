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
    val scope: String,
    val priority: String,
    /** yyyy-MM-dd */
    val scheduledDate: String,
    val subTitle: String,
    val expectedDuration: Int,
    /** hh:mm:ss */
    val startTime: String,
    /** hh:mm:ss */
    val endTime: String
)

fun List<PostAiPlansResponse>.toDomain(): Plan {

    val first = first()

    return Plan( // planId가 모두 동일하다는 가정 -> 첫 번째 것으로 Plan 데이터 채우기
        planId = first.planId,
        title = first.title,
        deadline = first.deadline,
        taskRange = first.scope,
        priority = PlanPriority.fromServer(first.priority),
        aiPlans = map { aiPlan ->
            AiPlan(
                aiPlanId = aiPlan.aiPlanId,
                scheduledDate = aiPlan.scheduledDate,
                startTime = aiPlan.startTime,
                endTime = aiPlan.endTime,
                subTitle = aiPlan.subTitle,
                expectedDuration = aiPlan.expectedDuration,
            )
        }
    )
}