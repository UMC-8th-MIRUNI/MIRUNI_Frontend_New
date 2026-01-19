package com.miruni.feature.aiplanner.data.dto.request

import com.miruni.feature.aiplanner.domain.model.PlanPriority
import com.miruni.feature.aiplanner.domain.model.PlanTimePeriod

data class PostAiPlansRequest(
    val title: String, // 일정 제목
    val deadline: String, // 마감 기한
    val timePeriod: PlanTimePeriod, // 실행 시간대
    val taskRange: String, // 일정 범위
    val priority: PlanPriority, // 우선순위
    val detailRequest: String // 세부 요청 사항
)
