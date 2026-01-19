package com.miruni.feature.aiplanner.domain.model

data class AiPlan(
    val aiPlanId: Long, // 세부 일정 ID,
    val scheduledDate: String, // 세부 일정 진행 날짜
    val startTime: String, // 시작 예정 시간
    val endTime: String, // 종료 예정 시간
    val subTitle: String, // 세부 일정 제목
    val expectedDuration: Int, // 예상 소요 시간
    val status: PlanStatus? = null // 일정 상태
)
