package com.miruni.feature.splash

import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.domain.auth.TokenDataStore
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

            // 스플래시 최소 시간 보장
            val elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime < minSplashTime) {
                delay(minSplashTime - elapsedTime)
            }

            if (!isOnboardingCompleted) {
                // 온보징 안 봄 -> 온보딩
                setEffect { SplashContract.Effect.Navigate.ToAppOnboarding }
            } else if (token.isNullOrBlank()) {
                // 온보딩 봄 & 토큰 없음 -> 로그인
                setEffect { SplashContract.Effect.Navigate.ToLogin }
            } else {
                // 둘 다 통과 -> 홈
                setEffect { SplashContract.Effect.Navigate.ToHome }
            }
        }
    }
}