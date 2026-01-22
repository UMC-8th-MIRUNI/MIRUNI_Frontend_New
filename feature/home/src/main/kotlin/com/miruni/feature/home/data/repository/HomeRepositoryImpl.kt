package com.miruni.feature.home.data.repository

import com.miruni.core.common.mapper.toDomainError
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.home.data.api.HomeApi
import com.miruni.feature.home.domain.model.HomePlanInfo
import com.miruni.feature.home.domain.model.UserInfo
import com.miruni.feature.home.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : HomeRepository {
    override suspend fun getHomePlan(): DataResult<HomePlanInfo, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getHomePlan()
        }

        // 통신 결과 처리
        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공 시
                val response = networkResult.data
                val serverResult = response.result

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
            is NetworkResult.Failure -> { // 통신 실패
                DataResult.Error(networkResult.error.toDomainError())
            }
        }
    }

    override suspend fun getHomeUser(): DataResult<UserInfo, DataError> {
        // 통신 실행
        val networkResult = executeApiRequest {
            api.getHomeUser()
        }

        // 통신 결과 처리
        return when(networkResult) {
            is NetworkResult.Success -> { // 통신 성공 시
                val response = networkResult.data
                val serverResult = response.result

                if (response.errorCode.isNullOrBlank() && serverResult != null ) {
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