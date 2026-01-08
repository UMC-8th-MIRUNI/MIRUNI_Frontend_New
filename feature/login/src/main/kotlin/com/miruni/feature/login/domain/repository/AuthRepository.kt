package com.miruni.feature.login.domain.repository

import com.miruni.feature.login.domain.model.AuthToken

interface AuthRepository {
    suspend fun login(id: String, password: String,autoLogin: Boolean) : AuthToken

}