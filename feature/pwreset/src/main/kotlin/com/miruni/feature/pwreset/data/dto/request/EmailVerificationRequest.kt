package com.miruni.feature.pwreset.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class EmailVerificationRequest(
    val email : String,
)
