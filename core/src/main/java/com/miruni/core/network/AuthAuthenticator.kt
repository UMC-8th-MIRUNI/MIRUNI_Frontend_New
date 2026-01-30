package com.miruni.core.network

import android.util.Log
import com.miruni.core.data.api.InternalRefreshApi
import com.miruni.core.data.dto.RefreshTokenRequest
import com.miruni.core.data.dto.RefreshTokenResponse
import com.miruni.core.domain.auth.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator(
    private val tokenDataStore: TokenDataStore
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { tokenDataStore.getRefreshToken() } ?: return null

        // 여러 api 요청이 동시에 만료할 수 있기 때문에 토큰 갱신 api 중복 호출 방지를 위하여
        // 딱 한번만 갱신하고 결과 공유
        synchronized(this) {
            val currentAccessToken = runBlocking { tokenDataStore.getAccessToken() }
            val request = response.request()

            if (request.header("Authorization") != "Bearer $currentAccessToken") {
                return request.newBuilder()
                    .header("Authorization", "Bearer $currentAccessToken")
                    .build()
            }

            return runBlocking {
                val refreshResponse = getNewToken(refreshToken)
                if (refreshResponse != null) {
                    tokenDataStore.saveAccessToken(refreshResponse.accessToken)
                    tokenDataStore.saveRefreshToken(refreshResponse.refreshToken)

                    request.newBuilder()
                        .header("Authorization", "Bearer ${refreshResponse.accessToken}")
                        .build()
                } else {
                    Log.e("AuthAuthenticator", "Token refresh failed, clearing tokens")
                    tokenDataStore.clear()
                    null
                }
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): RefreshTokenResponse? {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://miruni.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(InternalRefreshApi::class.java)
        return try {
            val response = service.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.errorCode == null) {
                response.result
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthAuthenticator", "Exception during token refresh", e)
            null
        }
    }
}