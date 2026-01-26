package com.miruni.feature.aiplanner.data.repository

import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.aiplanner.data.dto.response.PlanDto
import com.miruni.feature.aiplanner.data.dto.response.toDto
import com.miruni.core.common.mapper.toDomainError
import com.miruni.feature.aiplanner.domain.model.Plan
import com.miruni.feature.aiplanner.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    val api: AiPlannerApi
) : ScheduleRepository {
    override suspend fun getScheduleTable(id: Int): DataResult<Plan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getScheduleTable(planId = id)
        }
        // 통신 결과 처리
        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    val domainItem = serverResult.toDomain()
                    DataResult.Success(domainItem)
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

    override suspend fun updateScheduleTable(plan: Plan): DataResult<Plan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.patchScheduleTable(
                planId = plan.planId,
                request = PlanDto(
                    planId = plan.planId,
                    title = plan.title,
                    deadline = plan.deadline,
                    taskRange = plan.taskRange,
                    priority = plan.priority.server,
                    aiPlans = plan.aiPlans.map { it.toDto() }
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
                    val domainItem = serverResult.toDomain()
                    DataResult.Success(domainItem)
                } else {
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

    override suspend fun deleteScheduleAll(id: Int): DataResult<Unit, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.deleteScheduleTable(planId = id)
        }
        // 통신 결과 처리
        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                val isDeleted = serverResult?.isDeleted == true

                if (response.errorCode.isNullOrBlank() && isDeleted) {
                    // 삭제 성공 여부
                    DataResult.Success(Unit)
                } else {
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

    override suspend fun deleteScheduleItem(
        planId: Int,
        aiPlanIds: List<Int>
    ): DataResult<Unit, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.deleteScheduleItem(
                planId = planId,
                request = aiPlanIds
            )
        }
        // 통신 결과 확인
        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                val isDeleted = serverResult?.isDeleted == true

                if (response.errorCode.isNullOrBlank() && isDeleted)
                    DataResult.Success(Unit)
                else
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
            }
            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }
}