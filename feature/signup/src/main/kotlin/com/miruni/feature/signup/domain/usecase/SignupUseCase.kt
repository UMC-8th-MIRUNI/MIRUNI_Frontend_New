package com.miruni.feature.signup.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.signup.domain.model.User
import com.miruni.feature.signup.domain.repository.SignupRepository

class SignupUseCase(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(user: User): DataResult<Unit, DataError> {
        return signupRepository.signup(user)
    }
}