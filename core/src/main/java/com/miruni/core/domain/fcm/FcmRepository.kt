package com.miruni.core.domain.fcm

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult

interface FcmRepository {
    suspend fun registerFcmToken(
        token: String,
        deviceId: String,
        before5minAlarm: Boolean,
        before10minAlarm: Boolean,
        popupAlarm: Boolean,
        nagAlarm: Boolean
    ): DataResult<Unit, DataError>

    suspend fun updateFcmToken(
        deviceId: String,
        before5minAlarm: Boolean,
        before10minAlarm: Boolean,
        popupAlarm: Boolean,
        nagAlarm: Boolean
    ): DataResult<Unit, DataError>
}