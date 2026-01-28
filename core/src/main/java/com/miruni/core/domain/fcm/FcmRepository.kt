package com.miruni.core.domain.fcm

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult

interface FcmRepository {
    suspend fun registerFcmToken(token: String): DataResult<Unit, DataError>
}