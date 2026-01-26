package com.miruni.feature.login.data.datasource

import com.miruni.core.domain.auth.TokenDataStore
import com.miruni.feature.login.domain.datasource.AuthLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthLocalDataSourceImpl(
    private val tokenDataStore: TokenDataStore
) : AuthLocalDataSource {
    override suspend fun getAccessToken(): String? {
        return tokenDataStore.getAccessToken()
    }

    override suspend fun saveAccessToken(accessToken: String) {
        tokenDataStore.saveAccessToken(accessToken)
    }

    override suspend fun clearAccessToken() {
        tokenDataStore.clear()
    }
}