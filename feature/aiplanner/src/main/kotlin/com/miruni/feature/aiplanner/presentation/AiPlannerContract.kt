package com.miruni.feature.aiplanner.presentation

import com.miruni.feature.aiplanner.common.ViewEvent
import com.miruni.feature.aiplanner.common.ViewSideEffect
import com.miruni.feature.aiplanner.common.ViewState
import com.miruni.feature.aiplanner.domain.AiPlannerUiModel

object AiPlannerContract {
    sealed class Event : ViewEvent {
        object CompleteOnboarding : Event() // 온보딩 종료
    }

    data class State(
        val aiPlans: List<AiPlannerUiModel>? = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect
}