package com.miruni.feature.mypage.data.datasource

import android.util.Log
import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.core.network.executeApiRequest
import com.miruni.feature.mypage.data.api.ProfileApi
import com.miruni.feature.mypage.data.dto.request.ProfileRequest
import com.miruni.feature.mypage.data.dto.response.ProfileResponse
import com.miruni.feature.mypage.domain.datasource.ProfileRemoteDataSource

/**
 * 프로필 원격 데이터 소스 구현체
 */
class ProfileRemoteDataSourceImpl(
    private val profileApi: ProfileApi
) : ProfileRemoteDataSource {

    override suspend fun updateProfile(
        request: ProfileRequest
    ): NetworkResult<ApiResponse<ProfileResponse>> {
        Log.d(TAG, "updateProfile() called - request: $request")
        return executeApiRequest {
            profileApi.updateProfile(request).also {
                Log.d(TAG, "updateProfile() response: $it")
            }
        }
    }

    companion object {
        private const val TAG = "ProfileRemoteDataSource"
    }
}
