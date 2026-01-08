package com.miruni.feature.login.domain.datasource

interface AuthLocalDataSource {
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(accessToken: String)
    suspend fun clearAccessToken()
}