package com.miruni.feature.signup.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.signup.data.dto.request.EmailVerificationRequest
import com.miruni.feature.signup.data.dto.request.SignupRequest
import com.miruni.feature.signup.data.dto.request.VerifyCodeRequest
import com.miruni.feature.signup.data.dto.response.EmailVerificationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignupApi {
    @POST("/api/users/me/email-verification")
    suspend fun sendEmail(@Body request: EmailVerificationRequest)
            : ApiResponse<EmailVerificationResponse>
    @GET("/api/users/me/email-verification/confirm")
    suspend fun verifyCode(@Body request: VerifyCodeRequest)
            : ApiResponse<Unit>
    @POST("/api/users")
    suspend fun signUp(@Body request : SignupRequest) : ApiResponse<Unit>

}