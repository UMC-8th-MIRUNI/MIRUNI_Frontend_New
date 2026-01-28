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
    override suspend fun registerFcmToken(
        token: String,
        deviceId: String,
        before5minAlarm: Boolean,
        before10minAlarm: Boolean,
        popupAlarm: Boolean,
        nagAlarm: Boolean
    ): DataResult<Unit, DataError> {
        val result = fcmRemoteDataSource.registerFcmToken(
            FcmTokenRequest(
                token = token,
                deviceId = deviceId,
                before5minAlarm = before5minAlarm,
                before10minAlarm = before10minAlarm,
                popupAlarm = popupAlarm,
                nagAlarm = nagAlarm
            )
        )
        return handleResult(result)
    }

    override suspend fun updateFcmToken(
        deviceId: String,
        before5minAlarm: Boolean,
        before10minAlarm: Boolean,
        popupAlarm: Boolean,
        nagAlarm: Boolean
    ): DataResult<Unit, DataError> {
        val result = fcmRemoteDataSource.updateFcmToken(
            deviceId,
            FcmUpdateTokenRequest(
                before5minAlarm = before5minAlarm,
                before10minAlarm = before10minAlarm,
                popupAlarm = popupAlarm,
                nagAlarm = nagAlarm
            )
        )
        return handleResult(result)
    }

    private fun handleResult(result: NetworkResult<com.miruni.core.network.ApiResponse<Unit>>): DataResult<Unit, DataError> {
        return when (result) {
            is NetworkResult.Success -> {
                val response = result.data
                if (!response.errorCode.isNullOrBlank()) {
                    DataResult.Error(
                        DataError.CustomError(
                            code = response.errorCode,
                            msg = response.message ?: "요청 처리 중 문제가 발생했어요."
                        )
                    )
                } else {
                    DataResult.Success(Unit)
                }
            }

            is NetworkResult.Failure -> {
                DataResult.Error(result.error.toDomainError())
            }
        }
    }
}