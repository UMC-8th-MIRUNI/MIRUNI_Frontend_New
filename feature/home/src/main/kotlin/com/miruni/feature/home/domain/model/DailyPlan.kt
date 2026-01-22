package com.miruni.feature.home.domain.model

data class DailyPlan(
    val planType: PlanType, // 일정 유형
    val planId: Int, // AI 상위 일정 or 일반 일정 ID
    val title: String, // AI 상위 일정 or 일반 일정 제목
    val subTitle: String?, // 하위 일정 제목
    val startTime: String, // 시작 시간 "오전(오후) HH:mm"
    val endTime: String, // 종료 시간
    val priority: PlanPriority, // 우선 순위
    val isDone: Boolean // 완료 여부
)
