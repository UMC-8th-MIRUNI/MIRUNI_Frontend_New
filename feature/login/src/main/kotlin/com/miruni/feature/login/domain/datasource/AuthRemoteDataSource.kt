package com.miruni.feature.login.domain.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.feature.login.data.dto.request.GoogleLoginRequest
import com.miruni.feature.login.data.dto.request.KakaoLoginRequest
import com.miruni.feature.login.data.dto.request.LoginRequest
import com.miruni.feature.login.data.dto.request.RefreshTokenRequest
import com.miruni.feature.login.data.dto.response.LoginResponse

interface AuthRemoteDataSource {
    suspend fun getLogin(req: LoginRequest): NetworkResult<ApiResponse<LoginResponse>>
    suspend fun getKakaoLogin(req: KakaoLoginRequest): NetworkResult<ApiResponse<LoginResponse>>
    suspend fun getGoogleLogin(req: GoogleLoginRequest): NetworkResult<ApiResponse<LoginResponse>>
    suspend fun getRefreshToken(req: RefreshTokenRequest): NetworkResult<ApiResponse<LoginResponse>>
}
