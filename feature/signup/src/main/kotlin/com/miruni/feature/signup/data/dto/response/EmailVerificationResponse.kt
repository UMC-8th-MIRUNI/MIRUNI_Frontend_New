package com.miruni.feature.signup.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerificationResponse(
    val authCode : String
)