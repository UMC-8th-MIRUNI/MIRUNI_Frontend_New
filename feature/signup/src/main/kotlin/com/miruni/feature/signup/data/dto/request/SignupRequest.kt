package com.miruni.feature.signup.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val name : String,
    val email : String,
    val phoneNumber : String,
    val password : String,
    val nickname : String,
    val birthday : String
)
