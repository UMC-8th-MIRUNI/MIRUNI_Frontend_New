package com.miruni.feature.login.domain.datasource

import com.miruni.feature.login.data.dto.request.LoginRequest
import com.miruni.feature.login.data.dto.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    // ApiResponse로 바꿔야함 Flow<ApiResponse>
    suspend fun getLogin(loginRequest: LoginRequest) : Flow<LoginResponse>
}