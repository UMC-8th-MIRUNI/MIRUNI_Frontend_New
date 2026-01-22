package com.miruni.feature.home.data.api

import com.miruni.core.network.ApiResponse
import com.miruni.feature.home.data.dto.response.GetHomePlanResponse
import com.miruni.feature.home.data.dto.response.GetHomeUserResponse
import retrofit2.http.GET

interface HomeApi {

    /**
     * 홈페이지 일정 조회
     * - 오늘의 미완료 일정 조회
     */
    @GET("api/plans/home")
    suspend fun getHomePlan(): ApiResponse<GetHomePlanResponse>

    /**
     * 홈페이지 사용자 조회
     * - 사용자 닉네임 및 땅콩 개수 조회
     */
    @GET("api/users/home")
    suspend fun getHomeUser(): ApiResponse<GetHomeUserResponse>
}