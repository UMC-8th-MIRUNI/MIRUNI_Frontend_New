package com.miruni.core.domain.fcm

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import javax.inject.Inject

class UpdateFcmTokenUseCase(
    private val fcmRepository: FcmRepository
) {
    suspend operator fun invoke(
        deviceId: String,
        before5minAlarm: Boolean,
        before10minAlarm: Boolean,
        popupAlarm: Boolean,
        nagAlarm: Boolean
    ): DataResult<Unit, DataError> {
        return fcmRepository.updateFcmToken(
            deviceId = deviceId,
            before5minAlarm = before5minAlarm,
            before10minAlarm = before10minAlarm,
            popupAlarm = popupAlarm,
            nagAlarm = nagAlarm
        )
    }
}