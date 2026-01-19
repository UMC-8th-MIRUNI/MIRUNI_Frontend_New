package com.miruni.feature.aiplanner.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.domain.model.PlanProgress

interface MainRepository {
    /** AI 상위 일정 조회
     * - AI 일정들 가져오기
     */
    suspend fun getAiPlans(): DataResult<List<PlanProgress>, DataError>

    /** AI 플래닝 잔여 횟수 가져오기 */
    suspend fun getRemain(): DataResult<Int, DataError>
}