package com.miruni.feature.onboard

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.miruni.core.common.BaseViewModel
import com.miruni.core.domain.onboarding.OnboardingKey
import com.miruni.core.domain.onboarding.OnboardingRepository
import com.miruni.feature.onboard.presentation.model.OnboardingPageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) : BaseViewModel<OnboardContract.Event, OnboardContract.State, OnboardContract.Effect>() {

    override fun setInitialState(): OnboardContract.State {
        return OnboardContract.State(
            pageData = listOf(
                OnboardingPageData.Basic(
                    backgroundColor = Color(0xFF24C354),
                    header = "매일 다짐해도 바꾸기 어려운 미루는 습관,",
                    body = "MIRUNI는 계획부터 실천까지 합께합니다.",
                    imgRes = R.drawable.miruni_basic
                ),
                OnboardingPageData.Basic(
                    backgroundColor = Color(0xFF24C354),
                    header = "AI 플래너, 방해요소 차단, 일정 알림",
                    body = "모두 한 번에.",
                    imgRes = R.drawable.miruni_basic
                ),
                OnboardingPageData.Final(
                    backgroundColor = Color(0xFF24C354),
                    header = "습관은 분명 바뀔 수 있어요.",
                    body = "MIRUNI와 함께,\n더 효율적인 하루를 시작해보세요!",
                    topImgRes = R.drawable.onboarding_top_img,
                    bottomImgRes = R.drawable.onboarding_bottom_img
                )
            )
        )
    }

    override fun handleEvents(event: OnboardContract.Event) {
        when (event) {
            OnboardContract.Event.CompleteOnboarding -> completeOnboarding()
            OnboardContract.Event.SkipOnboarding -> completeOnboarding()
        }
    }

    private fun completeOnboarding() {
        viewModelScope.launch {
            onboardingRepository.completeOnboarding(OnboardingKey.APP_INTRO)
            setEffect { OnboardContract.Effect.NavigateToLogin }
        }
    }
}