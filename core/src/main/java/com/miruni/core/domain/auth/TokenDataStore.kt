package com.miruni.core.domain.auth

import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    suspend fun getAccessToken(): String?
    suspend fun getAccessTokenFlow(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    
    // Refresh Token 관련 추가
    suspend fun getRefreshToken(): String?
    suspend fun getRefreshTokenFlow(): Flow<String?>
    suspend fun saveRefreshToken(token: String)

    suspend fun clear()
}