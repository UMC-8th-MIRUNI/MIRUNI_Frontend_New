package com.miruni.feature.aiplanner.data.dto.request

data class DeleteScheduleItemRequest(
    val aiPlansIds: List<Int> // 삭제할 AI 플랜 ID
)
