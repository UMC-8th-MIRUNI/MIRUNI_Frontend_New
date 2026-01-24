package com.miruni.feature.signup.domain.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.feature.signup.data.dto.request.EmailVerificationRequest
import com.miruni.feature.signup.data.dto.request.SignupRequest
import com.miruni.feature.signup.data.dto.request.VerifyCodeRequest
import com.miruni.feature.signup.data.dto.response.EmailVerificationResponse

interface SignupRemoteDataSource {
    suspend fun sendEmailVerification(email : EmailVerificationRequest) : NetworkResult<ApiResponse<EmailVerificationResponse>>
    suspend fun verifyCode(authCode : VerifyCodeRequest) : NetworkResult<ApiResponse<Unit>>
    suspend fun signUp(request : SignupRequest) : NetworkResult<ApiResponse<Unit>>
}