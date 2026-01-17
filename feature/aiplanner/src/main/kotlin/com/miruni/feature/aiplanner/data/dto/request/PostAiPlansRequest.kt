package com.miruni.feature.aiplanner.data.dto.request

data class PostAiPlansRequest(
    val title: String, // 일정 제목
    val deadline: String, // 마감 기한
    val timePeriod: String, // 실행 시간대 - API 명세서 상 ENUM
    val taskRange: String, // 일정 범위
    val priority: String, // 우선순위 - API 명세서 상 ENUM
    val detailRequest: String // 세부 요청 사항
)
