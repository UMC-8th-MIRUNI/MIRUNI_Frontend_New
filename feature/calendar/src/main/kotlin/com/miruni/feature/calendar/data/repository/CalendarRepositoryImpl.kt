package com.miruni.feature.calendar.data.repository

import com.miruni.core.common.mapper.toDomainError
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.calendar.data.api.CalendarApi
import com.miruni.feature.calendar.data.dto.request.PatchPlanRequest
import com.miruni.feature.calendar.data.dto.request.PostPlanFinishRequest
import com.miruni.feature.calendar.data.dto.request.PostPlanRequest
import com.miruni.feature.calendar.domain.model.BasicPlan
import com.miruni.feature.calendar.domain.model.DailyPlans
import com.miruni.feature.calendar.domain.model.Day
import com.miruni.feature.calendar.domain.model.FinishPlan
import com.miruni.feature.calendar.domain.model.Plan
import com.miruni.feature.calendar.domain.model.PlanDraft
import com.miruni.feature.calendar.domain.model.PlanType
import com.miruni.feature.calendar.domain.repository.CalendarRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepositoryImpl @Inject constructor(
  private val api: CalendarApi
) : CalendarRepository {
    override suspend fun postPlanFinish(
        expectedTime: String
    ): DataResult<FinishPlan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.postPlanFinish(
                request = PostPlanFinishRequest(
                    expectedTime = expectedTime
                )
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
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

    override suspend fun getPlan(planId: Int, planType: PlanType): DataResult<Plan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getPlan(
                planId = planId,
                planType = planType.server
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
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

    override suspend fun getMonthlyPlanCount(
        year: Int,
        month: Int
    ): DataResult<List<Day>, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getMonthlyPlans(
                year = year,
                month = month
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.map { it.toDomain() }

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

    override suspend fun getDailyPlans(
        year: Int,
        month: Int,
        day: Int
    ): DataResult<DailyPlans, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getDailyPlans(
                year = year,
                month = month,
                day = day
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
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

    override suspend fun postPlan(draft: PlanDraft): DataResult<BasicPlan, DataError> {
        // 통신 성공
        val networkResult = executeApiRequest {
            api.postPlan(
                request = PostPlanRequest(
                    title = draft.title,
                    description = draft.description,
                    startDate = draft.startDate,
                    endDate = draft.endDate,
                    startTime = draft.startTime,
                    endTime = draft.endTime,
                    priority = draft.priority.server
                )
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
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

    override suspend fun deletePlan(basicPlanId: Int): DataResult<Int, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.deletePlan(
                basicPlanId = basicPlanId
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
                    val domainItem = serverResult.deletedPlanId

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

    override suspend fun editPlan(
        basicPlanId: Int,
        draft: PlanDraft
    ): DataResult<BasicPlan, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.patchPlan(
                basicPlanId = basicPlanId,
                request = PatchPlanRequest(
                    title = draft.title,
                    description = draft.description,
                    date = draft.startDate,
                    startTime = draft.startTime,
                    endTime = draft.endTime,
                    priority = draft.priority.server
                )
            )
        }

        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
                    // 성공시
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
}