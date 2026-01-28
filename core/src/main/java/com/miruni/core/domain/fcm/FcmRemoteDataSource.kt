package com.miruni.core.domain.fcm

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult

interface FcmRemoteDataSource {
    suspend fun registerFcmToken(token: String): NetworkResult<ApiResponse<Unit>>
}