package com.miruni.feature.mypage.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.mypage.data.dto.request.ProfileRequest
import com.miruni.feature.mypage.data.dto.response.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

/**
 * 프로필 관련 API 인터페이스
 */
interface ProfileApi {

    /**
     * 마이페이지 사용자 정보 조회
     * @return ApiResponse<ProfileResponse> 사용자 정보
     */
    @GET("/api/users/mypage")
    suspend fun getMyPageInfo(): ApiResponse<ProfileResponse>

    /**
     * 사용자 프로필 이미지와 닉네임 변경
     * @param profileRequest 프로필 변경 요청 데이터
     * @return ApiResponse<ProfileResponse> 변경된 프로필 정보
     */
    @PATCH("/api/users/profile")
    suspend fun updateProfile(
        @Body profileRequest: ProfileRequest
    ): ApiResponse<ProfileResponse>
}