package com.miruni.feature.aiplanner.domain.model

data class PlannerMain(
    val remainingAiCnt: Int,
    val plans: List<PlanPreview>
)
