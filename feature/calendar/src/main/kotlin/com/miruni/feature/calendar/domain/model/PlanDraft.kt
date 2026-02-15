package com.miruni.feature.calendar.domain.model

data class PlanDraft(
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String?, // 없으면 단일 일정
    val startTime: String,
    val endTime: String,
    val priority: PlanPriority
)