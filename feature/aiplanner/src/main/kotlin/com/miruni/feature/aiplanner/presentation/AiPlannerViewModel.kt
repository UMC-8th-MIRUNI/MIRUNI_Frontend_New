package com.miruni.feature.aiplanner.presentation

import androidx.lifecycle.viewModelScope
import com.miruni.feature.aiplanner.common.BaseViewModel
import com.miruni.feature.aiplanner.domain.AiPlannerRepository
import com.miruni.core.domain.onboarding.OnboardingRepository
import com.miruni.core.domain.onboarding.OnboardingKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiPlannerViewModel @Inject constructor(
    private val aiPlannerRepository: AiPlannerRepository,
    private val onboardingRepository: OnboardingRepository
) : BaseViewModel<AiPlannerContract.Event, AiPlannerContract.State, AiPlannerContract.Effect>() {

    override fun setInitialState() = AiPlannerContract.State()

    override fun handleEvents(event: AiPlannerContract.Event) {
        when (event) {
            AiPlannerContract.Event.CompleteOnboarding -> completeOnboarding()
        }
    }

    init { loadAiPlanner() }

    /**
     * 온보딩 완료 처리
     */
    private fun completeOnboarding() {
        viewModelScope.launch {
            onboardingRepository.completeOnboarding(OnboardingKey.AI_PLANNER)

            loadAiPlanner()
        }
    }

    /**
     * AI 플래너 스크린 로드
     */
    private fun loadAiPlanner() =
        viewModelScope.launch {
            val aiPlans = aiPlannerRepository.getAiPlans()
            setState { copy(aiPlans = aiPlans) }
        }
}