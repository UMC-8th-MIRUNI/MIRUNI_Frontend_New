package com.miruni.feature.pwreset.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.pwreset.domain.repository.PwRepository

class SendEmailVerifyUseCase(
    private val pwRepository: PwRepository
) {
    suspend operator fun invoke(email: String) : DataResult<String, DataError> {
        return pwRepository.sendEmailVerification(email)
    }
}