package com.miruni.feature.pwreset.data.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.feature.pwreset.data.api.PwApi
import com.miruni.feature.pwreset.data.dto.request.EmailVerificationRequest
import com.miruni.feature.pwreset.data.dto.response.EmailVerificationResponse
import com.miruni.feature.pwreset.domain.datasource.PwRemoteDataSource

class PwRemoteDataSourceImpl(
    private val pwApi: PwApi,
) : PwRemoteDataSource {
    override suspend fun sendEmailVerification(email: EmailVerificationRequest): NetworkResult<ApiResponse<EmailVerificationResponse>> =
        executeApiRequest {
            pwApi.sendEmail(email)
        }
}