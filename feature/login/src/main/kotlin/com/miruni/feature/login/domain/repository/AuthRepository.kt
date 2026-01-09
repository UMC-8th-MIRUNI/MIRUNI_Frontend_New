package com.miruni.feature.login.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.login.domain.model.AuthToken

interface AuthRepository {
    suspend fun login(id: String, password: String,autoLogin: Boolean) : DataResult<AuthToken, DataError>
    suspend fun kakaoLogin(accessToken: String) : AuthToken
    suspend fun googleLogin(idToken: String) : AuthToken
    suspend fun refresh(refreshToken: String) : AuthToken
}