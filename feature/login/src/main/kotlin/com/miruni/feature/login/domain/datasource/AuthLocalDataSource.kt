package com.miruni.feature.login.domain.datasource

import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(accessToken: String)
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun clearAccessToken()
    suspend fun saveAutoLogin()
}