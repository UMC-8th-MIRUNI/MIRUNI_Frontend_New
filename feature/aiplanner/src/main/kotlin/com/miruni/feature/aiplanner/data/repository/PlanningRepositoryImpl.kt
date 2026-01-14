package com.miruni.feature.aiplanner.data.repository

import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.domain.repository.PlanningRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanningRepositoryImpl @Inject constructor() : PlanningRepository {
    private val storage = mutableMapOf<String, PlanInput?>()
    private val state = MutableStateFlow(storage.toMap())

    override fun observeValues(): StateFlow<Map<String, PlanInput?>> = state

    override suspend fun setValue(id: String, value: PlanInput) {
        storage[id] = value
        state.value = storage.toMap()
    }
}