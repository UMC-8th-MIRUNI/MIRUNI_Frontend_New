package com.miruni.core.domain.fcm

import com.miruni.core.data.fcm.FcmTokenRequest
import com.miruni.core.data.fcm.FcmUpdateTokenRequest
import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult

interface FcmRemoteDataSource {
    suspend fun registerFcmToken(req: FcmTokenRequest): NetworkResult<ApiResponse<Unit>>
    suspend fun updateFcmToken(deviceId: String, req: FcmUpdateTokenRequest): NetworkResult<ApiResponse<Unit>>
}