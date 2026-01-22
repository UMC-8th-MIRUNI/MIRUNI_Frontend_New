package com.miruni.feature.pwreset.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult

interface PwRepository {
    suspend fun sendEmailVerification(email: String) : DataResult<String, DataError>
}