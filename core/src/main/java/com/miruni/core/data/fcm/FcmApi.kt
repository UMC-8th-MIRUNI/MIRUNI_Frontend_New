package com.miruni.core.data.fcm

import com.miruni.core.network.ApiResponse
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmApi {
    @POST("/api/fcm/token")
    suspend fun registerFcmToken(
        @Body fcmTokenRequest: FcmTokenRequest
    ): ApiResponse<Unit>
}

@Serializable
data class FcmTokenRequest(
    val token: String
)