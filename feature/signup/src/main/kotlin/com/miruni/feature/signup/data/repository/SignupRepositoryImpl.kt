package com.miruni.feature.signup.data.repository

import com.miruni.core.common.mapper.toDomainError
import com.miruni.core.network.NetworkResult
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.signup.data.dto.request.EmailVerificationRequest
import com.miruni.feature.signup.data.dto.request.VerifyCodeRequest
import com.miruni.feature.signup.data.mapper.toDto
import com.miruni.feature.signup.domain.datasource.SignupRemoteDataSource
import com.miruni.feature.signup.domain.model.User
import com.miruni.feature.signup.domain.repository.SignupRepository

class SignupRepositoryImpl(
    private val signupRemoteDataSource: SignupRemoteDataSource
) : SignupRepository {
    override suspend fun sendEmailVerification(email: String): DataResult<String, DataError> {
        return when (val result = signupRemoteDataSource.sendEmailVerification(
            EmailVerificationRequest(email)
        )) {
            is NetworkResult.Success ->{
                val response = result.data
                if (response.result == null) {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode
                                ?: "UNKNOWN",
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    DataResult.Success(response.result.toString())
                }
            }
            is NetworkResult.Failure -> DataResult.Error(result.error.toDomainError())
        }
    }

    override suspend fun verifyCode(authCode: String): DataResult<Unit, DataError> {
        return when (val result = signupRemoteDataSource.verifyCode(VerifyCodeRequest(authCode))){
            is NetworkResult.Success -> {
                val response = result.data
                if (response.errorCode == null) {
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
            is NetworkResult.Failure -> DataResult.Error(result.error.toDomainError())
        }
    }

    override suspend fun signup(user: User): DataResult<Unit, DataError> {
        return when(val result = signupRemoteDataSource.signUp(user.toDto())){
            is NetworkResult.Success -> {
                val response = result.data
                if (response.errorCode == null) {
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
            is NetworkResult.Failure -> DataResult.Error(result.error.toDomainError())
        }
    }

}