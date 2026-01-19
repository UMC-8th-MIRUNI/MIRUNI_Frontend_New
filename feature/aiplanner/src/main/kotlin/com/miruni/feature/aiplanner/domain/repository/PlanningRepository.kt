package com.miruni.feature.aiplanner.domain.repository

import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.domain.model.PlanPriority
import com.miruni.feature.aiplanner.domain.model.PlanTimePeriod
import kotlinx.coroutines.flow.Flow

interface PlanningRepository {
    fun observeValues(): Flow<Map<String, PlanInput?>>
    suspend fun setValue(id: String, value: PlanInput)

    /** AI 플래닝 */
    suspend fun postAiPlan(
        title: String,
        deadline: String,
        timePeriod: PlanTimePeriod,
        taskRange: String,
        priority: PlanPriority,
        detailRequest: String
    ): Plan
}