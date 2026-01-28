package com.miruni.core.data.fcm

import com.miruni.core.domain.fcm.FcmRemoteDataSource
import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest

class FcmRemoteDataSourceImpl(
    private val fcmApi: FcmApi
) : FcmRemoteDataSource {
    override suspend fun registerFcmToken(token: String): NetworkResult<ApiResponse<Unit>> {
        return executeApiRequest { fcmApi.registerFcmToken(FcmTokenRequest(token)) }
    }
}