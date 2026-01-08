package com.miruni.feature.login.data.datasource

import com.miruni.feature.login.data.dto.request.LoginRequest
import com.miruni.feature.login.data.dto.response.LoginResponse
import com.miruni.feature.login.domain.datasource.AuthRemoteDataSource
import kotlinx.coroutines.flow.Flow

class AuthRemoteDataSourceImpl(
//    private val authApi: AuthApi
) : AuthRemoteDataSource {
    override suspend fun getLogin(loginRequest: LoginRequest) : Flow<LoginResponse> {
        TODO("Not yet implemented")
    }
}