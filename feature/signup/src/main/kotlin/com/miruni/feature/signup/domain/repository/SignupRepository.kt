package com.miruni.feature.signup.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.signup.domain.model.User

interface SignupRepository {
    suspend fun sendEmailVerification(email: String) : DataResult<String, DataError>
    suspend fun verifyCode(authCode : String) : DataResult<Unit, DataError>
    suspend fun signup(user : User) : DataResult<Unit, DataError>
}