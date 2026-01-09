package com.miruni.feature.login.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.login.data.dto.request.GoogleLoginRequest
import com.miruni.feature.login.data.dto.request.KakaoLoginRequest
import com.miruni.feature.login.data.dto.request.LoginRequest
import com.miruni.feature.login.data.dto.request.RefreshTokenRequest
import com.miruni.feature.login.data.dto.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/token")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): ApiResponse<LoginResponse>
    @POST("/api/auth/social/google")
    suspend fun googleLogin(
        @Body googleLoginRequest: GoogleLoginRequest
    ): ApiResponse<LoginResponse>

    @POST("/api/auth/social/kakao")
    suspend fun kakaoLogin(
        @Body kakaoLoginRequest: KakaoLoginRequest
    ): ApiResponse<LoginResponse>

    @POST("/api/auth/token/refresh")
    suspend fun refreshToken(
        @Body refreshToken: RefreshTokenRequest
    ) : ApiResponse<LoginResponse>
}