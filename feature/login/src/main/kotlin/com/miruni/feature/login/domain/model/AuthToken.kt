package com.miruni.feature.login.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val isNewUser: Boolean? = null
)