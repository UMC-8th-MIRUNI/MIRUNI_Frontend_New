package com.miruni.feature.pwreset.domain.usecase

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.pwreset.domain.repository.PwRepository

class ResetPasswordUseCase(
    private val pwRepository: PwRepository
) {
    suspend operator fun invoke(password: String): DataResult<Unit, DataError>{
        return pwRepository.resetPassword(password)
    }
}