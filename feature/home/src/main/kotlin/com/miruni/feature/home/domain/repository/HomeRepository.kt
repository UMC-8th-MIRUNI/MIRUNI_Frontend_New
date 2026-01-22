package com.miruni.feature.home.domain.repository

import com.miruni.core.result.DataError
import com.miruni.core.result.DataResult
import com.miruni.feature.home.domain.model.HomePlanInfo
import com.miruni.feature.home.domain.model.UserInfo

interface HomeRepository {
    /**
     * 홈페이지 일정 조회
     * - 오늘의 미완료 일정 조회
     */
    suspend fun getHomePlan(): DataResult<HomePlanInfo, DataError>

    /**
     * 홈페이지 사용자 조회
     * - 사용자 닉네임 및 땅콩 개수 조회
     */
    suspend fun getHomeUser(): DataResult<UserInfo, DataError>
}