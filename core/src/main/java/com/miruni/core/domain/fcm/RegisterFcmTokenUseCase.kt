package com.miruni.core.domain.fcm

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult

class RegisterFcmTokenUseCase(
    private val fcmRepository: FcmRepository
) {
    suspend operator fun invoke(token: String): DataResult<Unit, DataError> {
        return fcmRepository.registerFcmToken(token)
    }
}