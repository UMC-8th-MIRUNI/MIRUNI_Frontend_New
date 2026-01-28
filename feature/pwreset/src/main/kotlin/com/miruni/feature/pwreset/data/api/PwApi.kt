package com.miruni.feature.pwreset.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.pwreset.data.dto.request.EmailVerificationRequest
import com.miruni.feature.pwreset.data.dto.request.ResetPasswordRequest
import com.miruni.feature.pwreset.data.dto.response.EmailVerificationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PwApi {
    @POST("/api/users/me/email-verification")
    suspend fun sendEmail(
        @Body body: EmailVerificationRequest
    ): ApiResponse<EmailVerificationResponse>

    @POST("/api/users/me/password/reset")
    suspend fun resetPassword(
        @Body body: ResetPasswordRequest
    ): ApiResponse<Unit>
}