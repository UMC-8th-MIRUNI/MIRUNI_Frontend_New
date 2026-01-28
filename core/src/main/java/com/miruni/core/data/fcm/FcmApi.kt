package com.miruni.core.data.fcm

import com.miruni.core.network.ApiResponse
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface FcmApi {
    @POST("/api/fcm/tokens")
    suspend fun registerFcmToken(
        @Body fcmTokenRequest: FcmTokenRequest
    ): ApiResponse<Unit>

    @PATCH("/api/fcm/tokens/{deviceId}")
    suspend fun updateFcmToken(
        @Path("deviceId") deviceId: String,
        @Body fcmUpdateTokenRequest: FcmUpdateTokenRequest
    ): ApiResponse<Unit>
}

@Serializable
data class FcmTokenRequest(
    val token: String,
    val deviceId: String,
    val before5minAlarm: Boolean,
    val before10minAlarm: Boolean,
    val popupAlarm: Boolean,
    val nagAlarm: Boolean
)

@Serializable
data class FcmUpdateTokenRequest(
    val before5minAlarm: Boolean,
    val before10minAlarm: Boolean,
    val popupAlarm: Boolean,
    val nagAlarm: Boolean
)