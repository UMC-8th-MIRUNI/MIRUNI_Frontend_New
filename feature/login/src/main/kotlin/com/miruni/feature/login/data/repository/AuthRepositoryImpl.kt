package com.miruni.feature.login.data.repository

import com.miruni.core.network.NetworkResult
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.login.data.dto.request.GoogleLoginRequest
import com.miruni.feature.login.data.dto.request.KakaoLoginRequest
import com.miruni.feature.login.data.dto.request.LoginRequest
import com.miruni.feature.login.data.mapper.toDomain
import com.miruni.feature.login.data.mapper.toDomainError
import com.miruni.feature.login.domain.datasource.AuthLocalDataSource
import com.miruni.feature.login.domain.datasource.AuthRemoteDataSource
import com.miruni.feature.login.domain.model.AuthToken
import com.miruni.feature.login.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {
    override suspend fun login(
        id: String,
        password: String,
        autoLogin: Boolean
    ): DataResult<AuthToken, DataError> {
        return when (val net = authRemoteDataSource.getLogin(LoginRequest(id, password))) {
            is NetworkResult.Success -> {
                val response = net.data
                if (!response.errorCode.isNullOrBlank()) {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    val result = response.result
                    if (result == null) {
                        DataResult.Error(DataError.DataNotFound)
                    } else {
                        authLocalDataSource.saveAccessToken(result.accessToken)
                        DataResult.Success(result.toDomain())
                    }
                }
            }

            is NetworkResult.Failure -> {
                DataResult.Error(net.error.toDomainError())
            }
        }
    }


    override suspend fun kakaoLogin(accessToken: String): DataResult<AuthToken, DataError> {
        return when (val net =
            authRemoteDataSource.getKakaoLogin(KakaoLoginRequest(kakaoAccessToken = accessToken))) {
            is NetworkResult.Success -> {
                val response = net.data

                if (!response.errorCode.isNullOrBlank()) {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    val result = response.result
                    if (result == null) {
                        DataResult.Error(DataError.DataNotFound)
                    } else {
                        // 로컬 저장
                        authLocalDataSource.saveAccessToken(result.accessToken)
                        DataResult.Success(result.toDomain())
                    }
                }
            }

            is NetworkResult.Failure -> {
                DataResult.Error(net.error.toDomainError())
            }
        }
    }

    override suspend fun googleLogin(idToken: String): DataResult<AuthToken, DataError> {
        return when (val net =
            authRemoteDataSource.getGoogleLogin(GoogleLoginRequest(googleIdToken = idToken))) {
            is NetworkResult.Success -> {
                val response = net.data

                if (!response.errorCode.isNullOrBlank()) {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    val result = response.result
                    if (result == null) {
                        DataResult.Error(DataError.DataNotFound)
                    } else {
                        // 로컬 저장
                        authLocalDataSource.saveAccessToken(result.accessToken)
                        // authLocalDataSource.saveRefreshToken(result.refreshToken)
                        DataResult.Success(result.toDomain())
                    }
                }
            }

            is NetworkResult.Failure -> {
                DataResult.Error(net.error.toDomainError())
            }
        }
    }


    override suspend fun refresh(refreshToken: String): AuthToken {
        TODO("Not yet implemented")

    }
}