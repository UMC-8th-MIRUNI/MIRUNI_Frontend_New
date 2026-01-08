package com.miruni.feature.login.data.repository

import com.miruni.feature.login.domain.datasource.AuthLocalDataSource
import com.miruni.feature.login.domain.datasource.AuthRemoteDataSource
import com.miruni.feature.login.domain.model.AuthToken
import com.miruni.feature.login.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {
    override suspend fun login(id: String, password: String,autoLogin: Boolean) : AuthToken {
        TODO("Not yet implemented")
    }
}