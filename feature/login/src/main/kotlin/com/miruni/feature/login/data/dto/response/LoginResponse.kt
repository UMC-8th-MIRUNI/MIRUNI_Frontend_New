package com.miruni.feature.login.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresIn : Long,
    val isNewUser : Boolean? = null
)
