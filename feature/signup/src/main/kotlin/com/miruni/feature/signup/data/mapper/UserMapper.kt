package com.miruni.feature.signup.data.mapper

import com.miruni.feature.signup.data.dto.request.SignupRequest
import com.miruni.feature.signup.domain.model.User

fun User.toDto() : SignupRequest = SignupRequest(
    name = name,
    email = email,
    nickname = nickname,
    phoneNumber = phoneNumber,
    password = password,
    birthday =  birthday
)