package com.miruni.network

import com.miruni.core.domain.auth.TokenDataStore
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
        cachedToken = tokenDataStore.getAccessToken()
    }

    fun getToken(): String? = cachedToken

    suspend fun updateToken(token: String) {
        cachedToken = token
        tokenDataStore.saveAccessToken(token)
    }

    suspend fun clear() {
        cachedToken = null
        tokenDataStore.clear()
    }
}