package com.miruni.feature.aiplanner.domain.model

data class PlanPreview(
    val planId: Int, // 상위 일정 ID
    val title: String, // 상위 일정 제목
    val doneCount: Int, // 세부 일정 중 완료 개수
    val totalCount: Int, // 세부 일정 전체 개수
    val progressRate: Int, // 진행률 (%)
    val isDone: Boolean, // 일정 전체에 대한 완료 여부
)
