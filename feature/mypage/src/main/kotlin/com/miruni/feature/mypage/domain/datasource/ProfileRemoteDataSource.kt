package com.miruni.feature.mypage.domain.datasource

import com.miruni.core.network.ApiResponse
import com.miruni.core.network.NetworkResult
import com.miruni.feature.mypage.data.dto.request.ProfileRequest
import com.miruni.feature.mypage.data.dto.response.ProfileResponse

/**
 * 프로필 원격 데이터 소스 인터페이스
 */
interface ProfileRemoteDataSource {

    /**
     * 마이페이지 사용자 정보 조회 API 호출
     * @return NetworkResult<ApiResponse<ProfileResponse>>
     */
    suspend fun getMyPageInfo(): NetworkResult<ApiResponse<ProfileResponse>>

    /**
     * 프로필 업데이트 API 호출
     * @param request 프로필 업데이트 요청 데이터
     * @return NetworkResult<ApiResponse<ProfileResponse>>
     */
    suspend fun updateProfile(
        request: ProfileRequest
    ): NetworkResult<ApiResponse<ProfileResponse>>
}
