package com.miruni.feature.aiplanner.domain

data class AiPlannerUiModel(
    val id: Long, // 상위 일정 ID
    val title: String, // 상위 일정 제목
    val isDone: Boolean, // 일정 전체에 대한 완료 여부
    val doneCount: Long, // 세부 일정 중 완료 개수
    val totalCount: Long, // 세부 일정 전체 개수
    val progressRate: Long // 진행률 (%)
)