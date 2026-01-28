package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.AiPlan
import com.miruni.feature.aiplanner.domain.model.PlanStatus

data class AiPlanDto(
    val aiPlanId: Int, // 세부 일정 ID,
    val scheduledDate: String, // 세부 일정 진행 날짜
    val startTime: String, // 시작 예정 시간
    val endTime: String, // 종료 예정 시간
    val subTitle: String, // 세부 일정 제목
    val expectedDuration: Int, // 예상 소요 시간
    val status: String? = null // 일정 상태
) {
    fun toDomain(): AiPlan {
        return AiPlan(
            aiPlanId = aiPlanId,
            scheduledDate = scheduledDate,
            startTime = startTime,
            endTime = endTime,
            subTitle = subTitle,
            expectedDuration = expectedDuration,
            status = PlanStatus.fromServer(status)
        )
    }
}

fun AiPlan.toDto(): AiPlanDto {
    return AiPlanDto(
        aiPlanId = this.aiPlanId,
        scheduledDate = this.scheduledDate,
        startTime = this.startTime,
        endTime = this.endTime,
        subTitle = this.subTitle,
        expectedDuration = this.expectedDuration,
        status = this.status?.server
    )
}