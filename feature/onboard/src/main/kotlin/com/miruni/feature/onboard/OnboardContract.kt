package com.miruni.feature.onboard

import com.miruni.core.common.ViewEvent
import com.miruni.core.common.ViewSideEffect
import com.miruni.core.common.ViewState
import com.miruni.feature.onboard.presentation.model.OnboardingPageData

class OnboardContract {
    sealed class Event : ViewEvent {
        data object CompleteOnboarding : Event()
        data object SkipOnboarding : Event()
    }

    data class State(
        val currentPage: Int = 0,
        val pageData: List<OnboardingPageData> = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToLogin : Effect()
    }
}