package com.miruni.feature.signup.domain.model

data class User(
    val name : String,
    val email : String,
    val phoneNumber : String,
    val password : String,
    val nickname : String,
    val birthday : String
)
