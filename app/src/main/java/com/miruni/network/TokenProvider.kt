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

    suspend fun init() {
        tokenDataStore.getAccessTokenFlow().collect { token ->
            cachedToken = token
            Log.d("Token/TokenProvider", "Token Updated: $token")
        }
    }

    fun getToken(): String? {
        return cachedToken ?: runBlocking {
            tokenDataStore.getAccessToken().also { cachedToken = it }
        }
    }

    suspend fun updateToken(token: String) {
        tokenDataStore.saveAccessToken(token)
    }

    suspend fun clear() {
        tokenDataStore.clear()
    }
}