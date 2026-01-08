package com.miruni.feature.login.data.datasource

import com.miruni.feature.login.domain.datasource.AuthLocalDataSource

class AuthLocalDataSourceImpl(
//    private val tokenDataStore: TokenDataStore
) : AuthLocalDataSource {
    override suspend fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun saveAccessToken(accessToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearAccessToken() {
        TODO("Not yet implemented")
    }
}