package com.miruni.network

import android.util.Log
import com.miruni.core.domain.auth.TokenDataStore
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

/**
 * - 현재 사용하는 Access Token에 대한 작업
 * - 비즈니스 로직 감추기 위함
 */
class TokenProvider @Inject constructor(
    private val tokenDataStore: TokenDataStore
) {
    private var cachedToken: String? = null
    private var cachedRefreshToken: String? = null

    suspend fun init() {
        tokenDataStore.getAccessTokenFlow().collect { token ->
            cachedToken = token
            Log.d("Token/TokenProvider", "Token Updated: $token")
        }
        tokenDataStore.getRefreshTokenFlow().collect { token ->
            cachedRefreshToken = token
            Log.d("Token/TokenProvider", "Refresh Token Updated: $token")
        }
    }

    fun getToken(): String? {
        return cachedToken ?: runBlocking {
            tokenDataStore.getAccessToken().also { cachedToken = it }
        }
    }

    fun getRefreshToken(): String? {
        return cachedRefreshToken ?: runBlocking {
            tokenDataStore.getRefreshToken().also { cachedRefreshToken = it }
        }
    }
    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        tokenDataStore.saveAccessToken(accessToken)
        tokenDataStore.saveRefreshToken(refreshToken)
        cachedToken = accessToken
        cachedRefreshToken = refreshToken
    }


    suspend fun clear() {
        tokenDataStore.clear()
        cachedToken = null
        cachedRefreshToken = null
    }
}