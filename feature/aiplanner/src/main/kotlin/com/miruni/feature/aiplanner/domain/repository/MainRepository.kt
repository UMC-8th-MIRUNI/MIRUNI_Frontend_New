package com.miruni.feature.aiplanner.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.domain.model.PlannerMain

interface MainRepository {
    /** AI 상위 일정 조회
     * - AI 플래닝 잔여 횟수
     * - AI 일정(상위)
     */
    suspend fun getAiPlans(): DataResult<PlannerMain, DataError>
}