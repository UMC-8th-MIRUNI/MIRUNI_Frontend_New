package com.miruni.feature.aiplanner.data.dto.request

import com.miruni.feature.aiplanner.domain.model.PlanPriority
import com.miruni.feature.aiplanner.domain.model.PlanTimePeriod

data class PostAiPlansRequest(
    val title: String, // 일정 제목
    val startDateTime: String, // 시작 날짜 "yyyy-MM-ddThh:mm:ss.000z"
    val endDateTime: String, // 종료 날짜
    val timePeriod: PlanTimePeriod, // 실행 시간대
    val scope: String, // 일정 범위
    val priority: PlanPriority, // 우선순위
    val detailRequest: String // 세부 요청 사항
)
