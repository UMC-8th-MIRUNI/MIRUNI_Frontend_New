package com.miruni.feature.aiplanner.domain.repository

import com.miruni.feature.aiplanner.presentation.model.AiPlannerUiModel
import com.miruni.feature.aiplanner.presentation.model.PlanUiModel

interface MainRepository {
    /** AI 플랜 가져오기 */
    suspend fun getAiPlans(): List<AiPlannerUiModel>

    /** AI 플래닝 잔여 횟수 가져오기 */
    suspend fun getRemain(): Int

    /** AI 플래닝 스케줄 표 조회 */
    suspend fun getSchedule(id: Long): PlanUiModel
}