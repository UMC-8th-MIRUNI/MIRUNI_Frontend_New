package com.miruni.feature.login.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.login.domain.model.AuthToken
import com.miruni.feature.login.domain.repository.AuthRepository

class GetGoogleLoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(googleIdToken: String) : DataResult<AuthToken, DataError> {
        return authRepository.googleLogin(googleIdToken)
    }
}