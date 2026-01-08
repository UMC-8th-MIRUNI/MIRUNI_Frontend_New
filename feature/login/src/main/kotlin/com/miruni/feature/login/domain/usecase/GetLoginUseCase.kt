package com.miruni.feature.login.domain.usecase

import com.miruni.feature.login.domain.model.AuthToken
import com.miruni.feature.login.domain.repository.AuthRepository

class GetLoginUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(id: String, password: String,autoLogin: Boolean) : AuthToken =
        authRepository.login(id, password, autoLogin)
}