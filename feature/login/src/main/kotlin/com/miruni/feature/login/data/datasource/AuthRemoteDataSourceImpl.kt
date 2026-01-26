package com.miruni.feature.login.data.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.feature.login.data.api.AuthApi
import com.miruni.feature.login.data.dto.request.GoogleLoginRequest
import com.miruni.feature.login.data.dto.request.KakaoLoginRequest
import com.miruni.feature.login.data.dto.request.LoginRequest
import com.miruni.feature.login.data.dto.request.RefreshTokenRequest
import com.miruni.feature.login.data.dto.response.LoginResponse
import com.miruni.feature.login.domain.datasource.AuthRemoteDataSource

class AuthRemoteDataSourceImpl(
    private val authApi: AuthApi
) : AuthRemoteDataSource {

    override suspend fun getLogin(req: LoginRequest): NetworkResult<ApiResponse<LoginResponse>> =
        executeApiRequest { authApi.login(req) }

    override suspend fun getKakaoLogin(req: KakaoLoginRequest): NetworkResult<ApiResponse<LoginResponse>> =
        executeApiRequest { authApi.kakaoLogin(req) }

    override suspend fun getGoogleLogin(req: GoogleLoginRequest): NetworkResult<ApiResponse<LoginResponse>> =
        executeApiRequest { authApi.googleLogin(req) }

    override suspend fun getRefreshToken(req: RefreshTokenRequest): NetworkResult<ApiResponse<LoginResponse>> =
        executeApiRequest { authApi.refreshToken(req) }
}
