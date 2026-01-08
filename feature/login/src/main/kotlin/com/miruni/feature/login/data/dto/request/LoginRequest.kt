package com.miruni.feature.login.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class KakaoLoginRequest(
    val kakaoAccessToken: String,
)

@Serializable
data class GoogleLoginRequest(
    val googleIdToken: String,
)