package com.miruni.feature.pwreset.data.repository

import com.miruni.core.common.mapper.toDomainError
import com.miruni.core.network.NetworkResult
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.pwreset.data.dto.request.EmailVerificationRequest
import com.miruni.feature.pwreset.domain.datasource.PwRemoteDataSource
import com.miruni.feature.pwreset.domain.repository.PwRepository

class PwRepositoryImpl(
    private val pwRemoteDataSource: PwRemoteDataSource,
) : PwRepository {
    override suspend fun sendEmailVerification(email: String): DataResult<String, DataError> {
        return when (val net = pwRemoteDataSource.sendEmailVerification(EmailVerificationRequest(email))) {
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
                        DataResult.Success(result.authCode)
                    }
                }
            }
            is NetworkResult.Failure -> {
                DataResult.Error(net.error.toDomainError())
            }

        }
    }
}