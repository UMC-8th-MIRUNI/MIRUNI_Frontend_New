package com.miruni.feature.signup.data.dto.request

import kotlinx.serialization.Serializable


@Serializable
data class VerifyCodeRequest(
    val authCode : String
)
