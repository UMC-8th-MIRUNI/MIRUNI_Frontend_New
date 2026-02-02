package com.miruni.core.domain.fcm

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import javax.inject.Inject

class RegisterFcmTokenUseCase(
    private val fcmRepository: FcmRepository
) {
    suspend operator fun invoke(
        token: String,
        deviceId: String,
        before5minAlarm: Boolean = true,
        before10minAlarm: Boolean = true,
        popupAlarm: Boolean = true,
        nagAlarm: Boolean = true
    ): DataResult<Unit, DataError> {
        return fcmRepository.registerFcmToken(
            token = token,
            deviceId = deviceId,
            before5minAlarm = before5minAlarm,
            before10minAlarm = before10minAlarm,
            popupAlarm = popupAlarm,
            nagAlarm = nagAlarm
        )
    }
}