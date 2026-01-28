package com.miruni.feature.pwreset.domain.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.feature.pwreset.data.dto.request.EmailVerificationRequest
import com.miruni.feature.pwreset.data.dto.request.ResetPasswordRequest
import com.miruni.feature.pwreset.data.dto.response.EmailVerificationResponse

interface PwRemoteDataSource {
    suspend fun sendEmailVerification(email : EmailVerificationRequest) : NetworkResult<ApiResponse<EmailVerificationResponse>>
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest) : NetworkResult<ApiResponse<Unit>>
}