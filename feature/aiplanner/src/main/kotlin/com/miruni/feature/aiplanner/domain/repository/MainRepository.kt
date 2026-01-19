package com.miruni.feature.aiplanner.domain.repository

import com.miruni.feature.aiplanner.presentation.model.AiPlannerUiModel

interface MainRepository {
    /** AI 상위 일정 조회
     * AI 일정들 가져오기
     */
    suspend fun getAiPlans(): List<AiPlannerUiModel>

    /** AI 플래닝 잔여 횟수 가져오기 */
    suspend fun getRemain(): Int
}