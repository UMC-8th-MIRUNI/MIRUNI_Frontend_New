package com.miruni.feature.login.data.mapper

import com.miruni.feature.login.data.dto.response.LoginResponse
import com.miruni.feature.login.domain.model.AuthToken

fun LoginResponse.toDomain(): AuthToken = AuthToken(
    accessToken = accessToken,
    refreshToken = refreshToken,
    tokenType = tokenType,
    expiresIn = expiresIn,
    isNewUser = isNewUser,
)