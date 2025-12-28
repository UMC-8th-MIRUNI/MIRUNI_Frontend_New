package com.miruni.feature.aiplanner.domain

interface AiPlannerRepository {
    /** AI 플랜 가져오기 */
    suspend fun getAiPlans(): List<AiPlannerUiModel>
}