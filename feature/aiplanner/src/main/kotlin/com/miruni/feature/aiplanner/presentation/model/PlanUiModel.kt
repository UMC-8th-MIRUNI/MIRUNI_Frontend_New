package com.miruni.feature.aiplanner.presentation.model

import com.miruni.feature.aiplanner.domain.model.AiPlan
import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.model.PlanPriority
import com.miruni.feature.aiplanner.domain.model.PlanStatus

/** 상위 일정 */
data class PlanUiModel(
    val planId: Int,
    val title: String,
    val deadline: String,
    val taskRange: String,
    val priority: String,
    val aiPlans: List<AiPlanUiModel>
) {
    fun toDomain(): Plan {
        return Plan(
            planId = planId,
            title = title,
            deadline = deadline,
            taskRange = taskRange,
            priority = PlanPriority.fromUi(priority),
            aiPlans = aiPlans.map { it.toDomain() }
        )
    }
}

fun Plan.toUiModel(): PlanUiModel {
    return PlanUiModel(
        planId = planId,
        title = title,
        deadline = deadline,
        taskRange = taskRange,
        priority = priority.ui,
        aiPlans = aiPlans.map { it.toUiModel() }
    )
}

/** AI 플랜 */
data class AiPlanUiModel(
    val aiPlanId: Int,
    val scheduledDate: String, // yyyy-MM-dd -> M/dd 변환 필요
    val startTime: String, // hh:mm:ss -> hh:mm 변환 필요
    val endTime: String, // hh:mm:ss -> hh:mm 변환 필요
    val content: String, // 세부 일정
    val expectedDuration: Int, // 예상 소요 시간
    val status: String? = null
) {
    fun toDomain(): AiPlan {
        return AiPlan(
            aiPlanId = aiPlanId,
            scheduledDate = scheduledDate,
            startTime = startTime,
            endTime = endTime,
            subTitle = content,
            expectedDuration = expectedDuration,
            status = status?.let { PlanStatus.fromUi(it) }
        )
    }
}

fun AiPlan.toUiModel(): AiPlanUiModel {
    return AiPlanUiModel(
        aiPlanId = aiPlanId,
        scheduledDate = scheduledDate,
        startTime = startTime,
        endTime = endTime,
        content = subTitle,
        expectedDuration = expectedDuration,
        status = status?.ui
    )
}