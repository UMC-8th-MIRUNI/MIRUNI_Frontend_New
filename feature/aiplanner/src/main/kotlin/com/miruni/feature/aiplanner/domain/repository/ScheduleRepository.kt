package com.miruni.feature.aiplanner.domain.repository

import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.presentation.model.PlanUiModel

/** AiPlannerScheduleScreen에서 사용 */
interface ScheduleRepository {
    /** AI 플래닝 스케줄 표 조회 */
    suspend fun getScheduleTable(id: Long): PlanUiModel

    /** AI 플래닝 스케줄표 수정 */
    suspend fun updateScheduleTable(plan: Plan): Plan

    /** AI 플래닝 스케줄표 삭제 */
    suspend fun deleteScheduleAll(): Boolean

    /** AI 플래닝 스케줄표 선택 삭제 */
    suspend fun deleteScheduleItem(
        aiPlanIds: List<Long>
    ): Boolean
}