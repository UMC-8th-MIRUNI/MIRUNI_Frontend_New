package com.miruni.feature.aiplanner.domain.repository

import com.miruni.feature.aiplanner.domain.model.PlanInput
import kotlinx.coroutines.flow.Flow

interface PlanningRepository {
    fun observeValues(): Flow<Map<String, PlanInput?>>
    suspend fun setValue(id: String, value: PlanInput)
}