package com.miruni.feature.aiplanner.data.dto.response

import com.miruni.feature.aiplanner.domain.model.PlanPreview

data class PlanPreviewDto(
    val planId: Int, // 상위 일정 ID
    val title: String, //
    val doneCnt: Int?, // 완료한 일정 갯수
    val totalCnt: Int?, // 총 분할 일정 갯수
    val progressRate: Int?, // 진행률
    val isDone: Boolean? // 완료 여부
) {
    fun toDomain(): PlanPreview {
        return PlanPreview(
            planId = planId,
            title = title,
            doneCount = doneCnt ?: 0,
            totalCount = totalCnt ?: 0,
            progressRate = progressRate ?: 0,
            isDone = isDone ?: false
        )
    }
}
