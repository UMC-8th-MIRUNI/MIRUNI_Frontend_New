package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.PlannerMain

data class GetAiPlansResponse(
    val remainingAiCnt: Int, // 잔여 AI 플래닝 횟수
    val plans: List<PlanPreviewDto> // 상위 일정 리스트
) {
    fun toDomain(): PlannerMain {
        return PlannerMain(
            remainingAiCnt =remainingAiCnt,
            plans = plans.map { it.toDomain() }
        )
    }
}
