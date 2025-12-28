package com.miruni.feature.aiplanner.domain

import com.miruni.feature.aiplanner.onboarding.OnboardingKey
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    /**
     * 특정 기능의 온보딩의 완료 여부 확인
     * @param key 어느 온보딩인지
     * @return 온보딩 완료 여부
     */
    fun isCompleted(key: OnboardingKey): Flow<Boolean>

    /**
     * 온보딩 완료 처리
     * @param key 어느 온보딩인지
     */
    suspend fun completeOnboarding(key: OnboardingKey)
}