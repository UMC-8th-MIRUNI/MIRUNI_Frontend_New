package com.miruni.feature.aiplanner.presentation

import com.miruni.feature.aiplanner.common.ViewEvent
import com.miruni.feature.aiplanner.common.ViewSideEffect
import com.miruni.feature.aiplanner.common.ViewState
import com.miruni.feature.aiplanner.domain.AiPlannerUiModel

object AiPlannerContract {
    sealed class Event : ViewEvent {
        object Enter : Event() // AiPlanner 진입
        object CompleteOnboarding : Event() // 온보딩 종료
    }

    data class State(
        val showOnboarding: Boolean = false, // 온보딩 노출할 지 여부
        val aiPlans: List<AiPlannerUiModel>? = emptyList()
    ) : ViewState

    sealed class Effect : ViewSideEffect
}