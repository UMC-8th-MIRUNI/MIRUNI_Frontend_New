package com.miruni.feature.signup.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.signup.domain.repository.SignupRepository

class SendEmailVerifyUseCase(
    private val signupRepository: SignupRepository,
) {
    suspend operator fun invoke(email: String) : DataResult<String, DataError> {
        return signupRepository.sendEmailVerification(email)
    }
}