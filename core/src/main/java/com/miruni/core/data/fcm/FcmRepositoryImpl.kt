package com.miruni.core.data.fcm

import com.miruni.core.common.mapper.toDomainError
import com.miruni.core.domain.fcm.FcmRemoteDataSource
import com.miruni.core.domain.fcm.FcmRepository
import com.miruni.core.network.NetworkResult
import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult

class FcmRepositoryImpl(
    private val fcmRemoteDataSource: FcmRemoteDataSource
) : FcmRepository {
    override suspend fun registerFcmToken(token: String): DataResult<Unit, DataError> {
        return when (val net = fcmRemoteDataSource.registerFcmToken(token)) {
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
                    DataResult.Success(Unit)
                }
            }

            is NetworkResult.Failure -> {
                DataResult.Error(net.error.toDomainError())
            }
        }
    }
}