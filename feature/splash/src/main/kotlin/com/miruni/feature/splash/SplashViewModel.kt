package com.miruni.feature.splash

import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.domain.auth.TokenDataStore
import com.miruni.core.domain.common.AppDataStore
import com.miruni.core.domain.common.AppDataStoreKeys
import com.miruni.core.domain.onboarding.OnboardingKey
import com.miruni.core.domain.onboarding.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val appDataStore: AppDataStore,
    private val onboardingRepository: OnboardingRepository
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {
    override fun setInitialState() = SplashContract.State()

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            SplashContract.Event.Initialize -> checkAppStatus()
        }
    }

    private fun checkAppStatus() {
        viewModelScope.launch {
            val minSplashTime = 1500L
            val startTime = System.currentTimeMillis()

            val isOnboardingCompleted = onboardingRepository.isCompleted(OnboardingKey.APP_INTRO).first()

            val token = tokenDataStore.getAccessToken()
            val isAutoLoginEnabled = appDataStore.get(AppDataStoreKeys.AUTO_LOGIN_ENABLED)

            // 스플래시 최소 시간 보장
            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime < minSplashTime) {
                delay(minSplashTime - elapsedTime)
            }

            if (!isOnboardingCompleted) {
                // 온보징 안 봄 -> 온보딩
                setEffect { SplashContract.Effect.Navigate.ToAppOnboarding }
            } else if (isAutoLoginEnabled == true && !token.isNullOrBlank()) {
                // 온보딩 봄 & 자동 로그인 활성화 & 토큰 존재 -> 홈
                setEffect { SplashContract.Effect.Navigate.ToHome }
            } else {
                // 그 외 (자동 로그인 비활성화 또는 토큰 없음) -> 로그인
                setEffect { SplashContract.Effect.Navigate.ToLogin }
            }
        }
    }
}