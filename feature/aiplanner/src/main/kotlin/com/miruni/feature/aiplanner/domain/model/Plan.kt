package com.miruni.feature.aiplanner.domain.model

data class Plan(
    val planId: Int, // 상위 일정 id
    val title: String, // 상위 일정 제목
    val deadline: String, // 마감기한
    val taskRange: String, // 일정 범위
    val priority: PlanPriority, // 우선 순위
    val progressRate: Int? = null, // 진행률 (%)
    val aiPlans: List<AiPlan> // 매칭되는 하위 일정
)