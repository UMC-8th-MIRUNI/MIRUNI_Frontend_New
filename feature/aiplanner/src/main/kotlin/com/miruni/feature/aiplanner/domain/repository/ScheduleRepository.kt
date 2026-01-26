package com.miruni.feature.aiplanner.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.domain.model.Plan

/** AiPlannerScheduleScreen에서 사용 */
interface ScheduleRepository {
    /** AI 플래닝 스케줄 표 조회 */
    suspend fun getScheduleTable(id: Int): DataResult<Plan, DataError>

    /** AI 플래닝 스케줄표 수정 */
    suspend fun updateScheduleTable(plan: Plan): DataResult<Plan, DataError>

    /** AI 플래닝 스케줄표 삭제 */
    suspend fun deleteScheduleAll(id: Int): DataResult<Boolean, DataError>

    /** AI 플래닝 스케줄표 선택 삭제 */
    suspend fun deleteScheduleItem(
        planId: Int,
        aiPlanIds: List<Int>
    ): DataResult<Boolean, DataError>
}