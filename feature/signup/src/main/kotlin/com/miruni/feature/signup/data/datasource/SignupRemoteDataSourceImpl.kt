package com.miruni.feature.signup.data.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.feature.signup.data.api.SignupApi
import com.miruni.feature.signup.data.dto.request.EmailVerificationRequest
import com.miruni.feature.signup.data.dto.request.SignupRequest
import com.miruni.feature.signup.data.dto.request.VerifyCodeRequest
import com.miruni.feature.signup.data.dto.response.EmailVerificationResponse
import com.miruni.feature.signup.domain.datasource.SignupRemoteDataSource

class SignupRemoteDataSourceImpl(
    private val signupApi: SignupApi
) : SignupRemoteDataSource {
    override suspend fun sendEmailVerification(email: EmailVerificationRequest): NetworkResult<ApiResponse<EmailVerificationResponse>> =
        executeApiRequest {
            signupApi.sendEmail(email)
        }

    override suspend fun verifyCode(authCode: VerifyCodeRequest): NetworkResult<ApiResponse<Unit>> =
        executeApiRequest {
            signupApi.verifyCode(authCode)
        }

    override suspend fun signUp(request: SignupRequest): NetworkResult<ApiResponse<Unit>> =
        executeApiRequest {
            signupApi.signUp(request)
        }
}