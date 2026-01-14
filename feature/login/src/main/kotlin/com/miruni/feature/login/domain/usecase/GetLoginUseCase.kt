package com.miruni.feature.login.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.login.domain.model.AuthToken
import com.miruni.feature.login.domain.repository.AuthRepository

class GetLoginUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(id: String, password: String,autoLogin: Boolean) : DataResult<AuthToken, DataError> {
        return authRepository.login(id, password, autoLogin)
    }
}