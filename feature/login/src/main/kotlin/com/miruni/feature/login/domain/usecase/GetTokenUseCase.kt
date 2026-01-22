package com.miruni.feature.login.domain.usecase

import com.miruni.feature.login.domain.repository.AuthRepository

class GetTokenUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke() = authRepository.getToken()
}