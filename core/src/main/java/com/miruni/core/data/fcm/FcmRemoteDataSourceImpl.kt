package com.miruni.core.data.fcm

import com.miruni.core.domain.fcm.FcmRemoteDataSource
import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest

class FcmRemoteDataSourceImpl(
    private val fcmApi: FcmApi
) : FcmRemoteDataSource {
    override suspend fun registerFcmToken(req: FcmTokenRequest): NetworkResult<ApiResponse<Unit>> {
        return executeApiRequest { fcmApi.registerFcmToken(req) }
    }

    override suspend fun updateFcmToken(deviceId: String, req: FcmUpdateTokenRequest): NetworkResult<ApiResponse<Unit>> {
        return executeApiRequest { fcmApi.updateFcmToken(deviceId, req) }
    }
}