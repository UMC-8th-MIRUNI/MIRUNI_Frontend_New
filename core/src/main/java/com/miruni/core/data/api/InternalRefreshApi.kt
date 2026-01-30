package com.miruni.core.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.core.data.dto.RefreshTokenRequest
import com.miruni.core.data.dto.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InternalRefreshApi {
    @POST("/api/auth/token/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): ApiResponse<RefreshTokenResponse>
}