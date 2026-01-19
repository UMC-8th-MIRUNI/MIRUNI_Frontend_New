package com.miruni.feature.aiplanner.data.repository

import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.aiplanner.data.api.AiPlannerApi
import com.miruni.feature.aiplanner.data.mapper.toDomainError
import com.miruni.feature.aiplanner.domain.model.PlanProgress
import com.miruni.feature.aiplanner.domain.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(
    private val api: AiPlannerApi
) : MainRepository {
    override suspend fun getAiPlans(): DataResult<List<PlanProgress>, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getAiPlans()
        }

        // 통신 결과 처리
        return when (networkResult) {
            is NetworkResult.Success -> { // 통신 성공 시
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

    override suspend fun getRemain(): DataResult<Int, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getRemain()
        }
        // 통신 결과 처리
        return when(networkResult) {
            is NetworkResult.Success -> {
                val response = networkResult.data
                val serverResult = response.result

                // 비즈니스 로직 확인
                if (response.errorCode.isNullOrBlank() && serverResult != null) {
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
            is NetworkResult.Failure -> {
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }
}