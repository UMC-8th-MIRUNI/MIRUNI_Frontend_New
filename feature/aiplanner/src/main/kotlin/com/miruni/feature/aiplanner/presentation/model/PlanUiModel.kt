package com.miruni.feature.aiplanner.presentation.model

/** 상위 일정 */
data class PlanUiModel(
    val planId: Long,
    val title: String,
    val deadline: String,
    val taskRange: String,
    val priority: String,
    val aiPlans: List<AiPlanUiModel>
)

/** AI 플랜 */
data class AiPlanUiModel(
    val aiPlanId: Long,
    val scheduledDate: String, // yyyy-MM-dd -> M/dd 변환 필요
    val startTime: String, // hh:mm:ss -> hh:mm 변환 필요
    val endTime: String, // hh:mm:ss -> hh:mm 변환 필요
    val content: String, // 세부 일정
    val expectedDuration: Int, // 예상 소요 시간
    val status: String? = null
)