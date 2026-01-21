package com.miruni.feature.aiplanner.data.repository

import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.aiplanner.data.dto.request.PostAiPlansRequest
import com.miruni.feature.aiplanner.data.mapper.toDomainError
import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.model.PlanInput
import com.miruni.feature.aiplanner.domain.model.PlanPriority
import com.miruni.feature.aiplanner.domain.model.PlanTimePeriod
import com.miruni.feature.aiplanner.domain.repository.PlanningRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanningRepositoryImpl @Inject constructor(
    private val api: AiPlannerApi
) : PlanningRepository {
    private val storage = mutableMapOf<String, PlanInput?>()
    private val state = MutableStateFlow(storage.toMap())

    override fun clear() {
        storage.clear()
        state.value = emptyMap()
    }
    override fun observeValues(): StateFlow<Map<String, PlanInput?>> = state

    override suspend fun setValue(id: String, value: PlanInput) {
        storage[id] = value
        state.value = storage.toMap()
    }

    override suspend fun postAiPlan(
        title: String,
        startDateTime: String,
        endDateTime: String,
        timePeriod: PlanTimePeriod,
        taskRange: String,
        priority: PlanPriority,
        detailRequest: String
    ): DataResult<List<Plan>, DataError> {// 통신 실행
        val networkResult = executeApiRequest {
            api.postAiPlans(
                PostAiPlansRequest(
                    title = title,
                    startDateTime = startDateTime,
                    endDateTime = endDateTime,
                    timePeriod = timePeriod.server,
                    scope = taskRange,
                    priority = priority.server,
                    detailRequest = detailRequest
                )
            )
        }

        // 통신 결과 처리
        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공 시
                    val domainList = serverResult.map { it.toDomain() }

                    DataResult.Success(domainList)
                } else {
                    // 통신 성공했으나 서버 에러
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                }
            }

            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }
}