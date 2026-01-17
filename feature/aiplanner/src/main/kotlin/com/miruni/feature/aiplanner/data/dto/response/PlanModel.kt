package com.miruni.feature.aiplanner.data.dto.response

data class PlanModel(
    val planId: Long, // 상위 일정 id
    val title: String, // 상위 일정 제목
    val deadline: String, // 마감기한
    val taskRange: String, // 일정 범위
    val priority: String, // 우선 순위 - API 명세서 상 ENUM
    val aiPlans: List<AiPlanModel> // 매칭되는 하위 일정
)
