package com.miruni.feature.signup.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.signup.domain.repository.SignupRepository

class VerifyEmailUseCase(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(authCode: String): DataResult<Unit, DataError> {
        return signupRepository.verifyCode(authCode)
    }
}