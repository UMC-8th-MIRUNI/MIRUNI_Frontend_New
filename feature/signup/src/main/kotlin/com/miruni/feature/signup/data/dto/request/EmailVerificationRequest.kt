package com.miruni.feature.signup.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerificationRequest(
    val email : String,
)